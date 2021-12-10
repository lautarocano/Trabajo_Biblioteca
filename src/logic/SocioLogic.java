package logic;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import servlets.Servlet;

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
	//Considerar agregar validaciones de campos en un futuro (Ejemplo: mail válido)
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
	//Falta agregar validación de disponibilidad de libros y de fecha de reserva
	public void retiraReservaYRealizaPrestamo(Prestamo pres, Reserva reserva) throws SQLException, BusinessLogicException {
		int cantMaxLibrosPend;
		int diasPoliticaPrestamo;
		int diasMaximoLibro;
		try {
			PrestamoLogic pLogic = new PrestamoLogic();
			PoliticaPrestamoLogic ppl = new PoliticaPrestamoLogic();
			ReservaLogic rl = null;
			cantMaxLibrosPend = pLogic.getLimiteLibrosPendientes();
			diasPoliticaPrestamo = ppl.getActual().getDiasPrestamo();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			if (!sdf.format(reserva.getFechaReserva()).equals(sdf.format(date))) {
				throw new BusinessLogicException("Las reservas se retiran únicamente el día de la fecha pactada. Sin excepción");
			}
			else if (this._SocioDAO.isSancionado(pres.getSocio())) {
				throw new BusinessLogicException("El socio no puede retirar libros debido a que está sancionado.");
			}
			else if (this._SocioDAO.hasOverdueLoan(pres.getSocio())) {
				throw new BusinessLogicException("El socio tiene préstamos atrasados pendientes de devolución.");
			}
			else if (this._SocioDAO.getNotReturnedBooks(pres.getSocio())+pres.getLineasPrestamo().size() > cantMaxLibrosPend) {
				throw new BusinessLogicException("La cantidad ingresada sumada a los libros pendientes de devolución supera el límite establecido.");
			}
			else {
				rl = new ReservaLogic();
				pres.setDiasPrestamo(diasPoliticaPrestamo);
				for (LibroReserva l: reserva.getLibros()) {
					diasMaximoLibro = rl.getDiasMaximoPrestamo(l.getLibro(), diasPoliticaPrestamo, reserva);
					if (diasMaximoLibro == 0) {
						throw new BusinessLogicException("El libro \""+l.getLibro().getTitulo()+"\" no está disponible.");
					}
					else if (diasMaximoLibro < pres.getDiasPrestamo()) {
						pres.setDiasPrestamo(diasMaximoLibro);
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
				throw new BusinessLogicException("No puede realizar reservas debido a que está sancionado.");
			}
			else if (this._SocioDAO.hasOverdueLoan(res.getSocio())) {
				throw new BusinessLogicException("Usted tiene préstamos atrasados pendientes de devolución, entréguelos para poder realizar reservas.");
			}
			else {
				ReservaDAO rDAO = new ReservaDAO();
				for (LibroReserva l: res.getLibros()) {
					if (!rDAO.getFechasDisponible(l.getLibro(), 2).contains('"'+res.getFechaReserva().toString()+'"')) {
						throw new BusinessLogicException("El libro \""+l.getLibro().getTitulo()+"\" no está disponible para la fecha ingresada.");
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
				Servlet.enviarConGMail(socio.getEmail(), "Sanción por préstamo atrasado", "Nos comunicamos con usted para informarle que debido a la entrega tardía de los libros prestados "
						+ "estará imposibilitado de realizar nuevas reservas y/o retiros por "+sancion.getDiasSancion()+" días.");
			}
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
}