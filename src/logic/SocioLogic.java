package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import data.PoliticaSancionDAO;
import data.ReservaDAO;
import data.SancionDAO;
import data.SocioDAO;
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
	
	public void insert(Socio socio) throws SQLException {
		try {
			if (!this.dniAlreadyExists(socio.getDni())) 
				this._SocioDAO.insert(socio);
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
	//Falta agregar validación de disponibilidad de libros
	public void realizaPrestamo(Prestamo pres) throws SQLException {
		int cantMaxLibrosPend;
		try {
			PrestamoLogic pLogic = new PrestamoLogic();
			cantMaxLibrosPend = pLogic.getLimiteLibrosPendientes();
			if (!this._SocioDAO.isSancionado(pres.getSocio())) {
				
			}
			else if (!this._SocioDAO.hasOverdueLoan(pres.getSocio())) {
				
			}
			else if (this._SocioDAO.getNotReturnedBooks(pres.getSocio())+pres.getLineasPrestamo().size() > cantMaxLibrosPend) {
				
			}
			else {
				pLogic.insert(pres);
			}
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void realizaReserva(Reserva res) throws SQLException {
		int cantMaxLibrosPend;
		try {
			PrestamoLogic pLogic = new PrestamoLogic();
			cantMaxLibrosPend = pLogic.getLimiteLibrosPendientes();
			
			if (!this._SocioDAO.isSancionado(res.getSocio())) {
				
			}
			else
			if (!this._SocioDAO.hasOverdueLoan(res.getSocio())) {
				
			}
			else if (this._SocioDAO.getNotReturnedBooks(res.getSocio())+res.getLibros().size() > cantMaxLibrosPend) {
				
			}
			else {
				ReservaDAO rDAO = new ReservaDAO();
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