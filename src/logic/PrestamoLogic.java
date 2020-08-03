package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.PrestamoDAO;
import model.Prestamo;
import model.Socio;

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
	
	public ArrayList<Prestamo> getAllBySocio(Socio socio) throws SQLException {
		try {
			return this._PrestamoDAO.getAllBySocio(socio);
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
}