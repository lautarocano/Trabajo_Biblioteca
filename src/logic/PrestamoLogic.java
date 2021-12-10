package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import data.PrestamoDAO;
import model.Prestamo;
import model.Socio;
import servlets.Servlet;

public class PrestamoLogic {

private PrestamoDAO _PrestamoDAO;
	
	public PrestamoLogic() {
		this._PrestamoDAO = new PrestamoDAO();
	}
	
	public void insert(Prestamo pres) throws SQLException {
		try {
			this._PrestamoDAO.insert(pres);
		}
		catch (SQLException exception) {
			throw exception;
		}	
	}
	
	public void update(Prestamo pres) throws SQLException {
		try {
			this._PrestamoDAO.update(pres);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(Prestamo pres) throws SQLException {
		try {
			this._PrestamoDAO.delete(pres);
		}
		catch (SQLException exception) {
			throw exception;
		}	
	}
	
	public ArrayList<Prestamo> getAll() throws SQLException {
		try {
			return this._PrestamoDAO.getAll();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Prestamo> getAllPendientes() throws SQLException {
		try {
			return this._PrestamoDAO.getAllPendientes();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Prestamo> getAllBySocio(Socio socio) throws SQLException {
		try {
			return this._PrestamoDAO.getAllBySocio(socio);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	public ArrayList<Prestamo> getAllPendientesBySocio(Socio socio) throws SQLException {
		try {
			return this._PrestamoDAO.getAllPendientesBySocio(socio);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}

	
	public Prestamo getOne(int id) throws SQLException {
		try {
			return this._PrestamoDAO.getOne(id);
		}
		catch (SQLException exception) {
			throw exception;
		}	
	}
	
	public int getLimiteLibrosPendientes() throws SQLException {
		try {
			return this._PrestamoDAO.getLimiteLibrosPendientes();
		}
		catch (SQLException exception) {
			throw exception;
		}	
	}
	
	public void endLoan(Prestamo pres) throws SQLException {	
		try {
			this._PrestamoDAO.endLoan(pres);
		}
		catch (SQLException exception) {
			throw exception;
		}	
	}
	
	public void updateLoans() throws SQLException {
		Date fechaActual = new Date();
		try {
			ArrayList<Prestamo> prestamos = this._PrestamoDAO.getAllPrestamosEnCurso();
			for (Prestamo pres : prestamos) {
				int duracionPrestamo = (int) ((fechaActual.getTime() - pres.getFechaPrestamo().getTime())/86400000);
				int diferencia = duracionPrestamo - pres.getDiasPrestamo();
				if (diferencia > 0) {
					this._PrestamoDAO.actualizarEstado(pres);
				}
				else if (diferencia == 0) {
					Servlet.enviarConGMail(pres.getSocio().getEmail(), "Préstamo biblioteca", "Hoy es el último día para devolver los libros de su préstamo. Número de préstamo: "+pres.getId());
				}
			}
		}
		catch (SQLException exception) {
			throw exception;
		}	
	}
	
	public void notificarAtraso() throws SQLException {
		try {
			ArrayList<Prestamo> prestamos = this._PrestamoDAO.getAllPrestamosAtrasados();
			for (Prestamo pres : prestamos) {
				Servlet.enviarConGMail(pres.getSocio().getEmail(), "Préstamo biblioteca atrasado", "Buenas noches, por favor recuerde que cuenta con un préstamo en curso cuya devolución está atrasada. Favor de acercar el libro cuanto antes. Muchas gracias. Número de préstamo: "+pres.getId());
			}
		}
		catch (SQLException exception) {
			throw exception;
		}	
	}
}