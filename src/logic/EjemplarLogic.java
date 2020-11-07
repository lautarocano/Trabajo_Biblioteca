package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.EjemplarDAO;
import model.Ejemplar;

public class EjemplarLogic {
private EjemplarDAO _EjemplarDAO;
	
	public EjemplarLogic() {
		this._EjemplarDAO = new EjemplarDAO();
	}
	
	public ArrayList<Ejemplar> getAll() throws SQLException {
		try {
			return this._EjemplarDAO.getAll();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Ejemplar> getAllByLibro(int idLibro) throws SQLException {
		try {
			return this._EjemplarDAO.getAllByLibro(idLibro);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void insert(Ejemplar e) throws SQLException {
		try {
			this._EjemplarDAO.insert(e);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(Ejemplar e) throws SQLException {
		try {
			this._EjemplarDAO.delete(e);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
}
