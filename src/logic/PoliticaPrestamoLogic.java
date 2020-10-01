package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.PoliticaPrestamoDAO;
import model.PoliticaPrestamo;

public class PoliticaPrestamoLogic {
	private PoliticaPrestamoDAO _PoliticaPrestamoDAO;
	public PoliticaPrestamoLogic() {
		this._PoliticaPrestamoDAO = new PoliticaPrestamoDAO();
	}
	
	public ArrayList<PoliticaPrestamo> getAll() throws SQLException {
		try {
			return this._PoliticaPrestamoDAO.getAll();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public PoliticaPrestamo getOne(int id) throws SQLException {
		try {
			return this._PoliticaPrestamoDAO.getOne(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void insert(PoliticaPrestamo pp) throws SQLException {
		try {
				this._PoliticaPrestamoDAO.insert(pp);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void update(PoliticaPrestamo pp) throws SQLException {
		try {
				this._PoliticaPrestamoDAO.update(pp);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(PoliticaPrestamo pp) throws SQLException {
		try {
			this._PoliticaPrestamoDAO.delete(pp);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
}
