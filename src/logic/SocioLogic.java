package logic;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import data.PoliticaSancionDAO;
import data.ReservaDAO;
import data.SancionDAO;
import data.SocioDAO;
import model.LibroReserva;
import model.PoliticaSancion;
import model.Prestamo;
import model.Reserva;
import model.Sancion;
import model.Socio;

public class SocioLogic {
	private SocioDAO _SocioDAO;
	
	public SocioLogic() {
		this._SocioDAO = new SocioDAO();
	}
	
	public ArrayList<Socio> getAll() throws SQLException {
		try {
			return this._SocioDAO.getAll();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Socio getOne(int id) throws SQLException {
		try {
			return this._SocioDAO.getOne(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Socio getOneByUser(int idUser) throws SQLException{
		try {
			return this._SocioDAO.getOneByUser(idUser);
		} catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Socio getOneByEmail(String email) throws SQLException{
		try {
			return this._SocioDAO.getOneByEmail(email);
		} catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void insert(Socio socio) throws SQLException,Exception {
		try {
			if (!this.dniAlreadyExists(socio.getDni())) {
				this._SocioDAO.insert(socio);
			}
			else {
				throw new Exception("Ya existe un socio con el dni ingresado");
			}
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	//Considerar agregar validaciones de campos en un futuro (Ejemplo: mail v�lido)
	public void update(Socio socio) throws SQLException {
		try {
			this._SocioDAO.update(socio);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(Socio socio) throws SQLException {
		try {
			this._SocioDAO.delete(socio);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public boolean dniAlreadyExists(int dni) throws SQLException {
		try {
			return this._SocioDAO.dniAlreadyExists(dni);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	//Falta agregar validaci�n de disponibilidad de libros y de fecha de reserva
	public void retiraReservaYRealizaPrestamo(Prestamo pres, Reserva reserva) throws SQLException, BusinessLogicException {
		int cantMaxLibrosPend;
		try {
			PrestamoLogic pLogic = new PrestamoLogic();
			ReservaLogic rl = null;
			cantMaxLibrosPend = pLogic.getLimiteLibrosPendientes();
			if (this._SocioDAO.isSancionado(pres.getSocio())) {
				throw new BusinessLogicException("El socio no puede retirar libros debido a que est� sancionado.");
			}
			else if (this._SocioDAO.hasOverdueLoan(pres.getSocio())) {
				throw new BusinessLogicException("El socio tiene pr�stamos atrasados pendientes de devoluci�n.");
			}
			else if (this._SocioDAO.getNotReturnedBooks(pres.getSocio())+pres.getLineasPrestamo().size() > cantMaxLibrosPend) {
				throw new BusinessLogicException("La cantidad ingresada sumada a los libros pendientes de devoluci�n supera el l�mite establecido.");
			}
			else {
				rl = new ReservaLogic();
				ReservaDAO rDAO = new ReservaDAO();
				Date fechaInicio = pres.getFechaPrestamo();
				LocalDateTime fechaFin = LocalDateTime.from(fechaInicio.toInstant()).plusDays(pres.getDiasPrestamo());
				LocalDateTime date =  LocalDateTime.from(fechaInicio.toInstant()).plusDays(1);
				ArrayList<String> fechas = new ArrayList<String>();
				while (date.isBefore(fechaFin)) {
					fechas.add('"'+date.toString()+'"');
					date=date.plusDays(1);
				}
				for (LibroReserva l: reserva.getLibros()) {
					if (!rDAO.getFechasDisponible(l.getLibro(), 2).containsAll(fechas)) {
						throw new BusinessLogicException("El libro \""+l.getLibro().getTitulo()+"\" no est� disponible para la fecha y d�as de pr�stamo ingresados.");
					}
				}
				pLogic.insert(pres);
				rl.entregarReserva(reserva);
			}
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void realizaReserva(Reserva res) throws SQLException, BusinessLogicException {
		try {			
			if (this._SocioDAO.isSancionado(res.getSocio())) {
				throw new BusinessLogicException("No puede realizar reservas debido a que est� sancionado.");
			}
			else if (this._SocioDAO.hasOverdueLoan(res.getSocio())) {
				throw new BusinessLogicException("Usted tiene pr�stamos atrasados pendientes de devoluci�n, entr�guelos para poder realizar reservas.");
			}
			else {
				ReservaDAO rDAO = new ReservaDAO();
				for (LibroReserva l: res.getLibros()) {
					if (!rDAO.getFechasDisponible(l.getLibro(), 2).contains('"'+res.getFechaReserva().toString()+'"')) {
						throw new BusinessLogicException("El libro \""+l.getLibro().getTitulo()+"\" no est� disponible para la fecha ingresada.");
					}
				}
				rDAO.insert(res);
			}
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void entregaPrestamo(Prestamo pres) throws SQLException {
		try {
			PrestamoLogic pLogic = new PrestamoLogic();
			Date fechaActual = new Date();
			int duracionPrestamo = (int) ((fechaActual.getTime() - pres.getFechaPrestamo().getTime())/86400000);
			int diferencia = duracionPrestamo - pres.getDiasPrestamo();
			if (diferencia > 0) {
				this.penalize(pres.getSocio(), diferencia);
			}
			pLogic.endLoan(pres);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void penalize(Socio socio, int dias) throws SQLException {
		try {
			PoliticaSancionDAO _PoliticaSancionDAO = new PoliticaSancionDAO();
			PoliticaSancion politicaSancion= _PoliticaSancionDAO.getOneByDiasAtraso(dias);
			if(politicaSancion!=null) {
				SancionDAO _SancionDAO=new SancionDAO();
				Sancion sancion=new Sancion();
				sancion.setDiasSancion(politicaSancion.getDiasDeSancion());
				sancion.setFechaSancion(Calendar.getInstance().getTime());
				sancion.setSocio(socio);
				_SancionDAO.insert(sancion);
			}
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
}