package logic;
import java.sql.SQLException;
import java.util.ArrayList;

import data.GeneroDAO;
import model.Genero;

public class GeneroLogic {
	private GeneroDAO _GeneroDAO;
	
	public GeneroLogic() {
		this._GeneroDAO = new GeneroDAO();
	}
	
	public ArrayList<Genero> getAll() throws SQLException {
		try {
			return this._GeneroDAO.getAll();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Genero getOne(int id) throws SQLException {
		try {
			return this._GeneroDAO.getOne(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void insert(Genero gen) throws SQLException {
		try {
				this._GeneroDAO.insert(gen);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void update(Genero gen) throws SQLException {
		try {
				this._GeneroDAO.update(gen);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(Genero gen) throws SQLException {
		try {
			this._GeneroDAO.delete(gen);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
}