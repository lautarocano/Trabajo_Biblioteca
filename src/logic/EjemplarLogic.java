package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.EjemplarDAO;
import model.Ejemplar;
import model.Libro;

public class EjemplarLogic {
private EjemplarDAO _EjemplarDAO;
	
	public EjemplarLogic() {
		this._EjemplarDAO = new EjemplarDAO();
	}
	
	public Ejemplar getOne(int idEjemplar) throws SQLException {
		try {
			return this._EjemplarDAO.getOne(idEjemplar);
		}
		catch (SQLException exception) {
			throw exception;
		}
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
	
	public ArrayList<Ejemplar> getAllDisponibles(Libro lib) throws SQLException {
		try {
			return this._EjemplarDAO.getAllDisponibles(lib);
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
