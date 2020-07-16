package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.LibroDAO;
import model.Genero;
import model.Libro;

public class LibroLogic {
	
private LibroDAO _LibroDAO;
	
	public LibroLogic() {
		this._LibroDAO = new LibroDAO();
	}
	
	
	
	public ArrayList<Libro> getAll() throws SQLException {
		
		try {
			return this._LibroDAO.getAll();
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public Libro getOne(int id) throws SQLException {
		
		try {
			return this._LibroDAO.getOne(id);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public ArrayList<Libro> getAllByGenero(Genero genero) throws SQLException {
		try {
			return this._LibroDAO.getAllByGenero(genero);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public ArrayList<Libro> getAllByTitulo(String titulo) throws SQLException {
		
		try {
			return this._LibroDAO.getAllByTitulo(titulo);
		}
		catch (SQLException exception){
			throw exception;
		}
	}


	public void insert(Libro lib) throws SQLException {
	try {
			this._LibroDAO.insert(lib);
	}
	catch (SQLException exception){
		throw exception;
	}
	
	}
	
	public void update(Libro lib) throws SQLException {
		try {
			this._LibroDAO.update(lib);
	}
	catch (SQLException exception){
		throw exception;
	}
		
	}
	
	public void delete(Libro lib) throws SQLException {
		
		try {
			this._LibroDAO.delete(lib);
	}
	catch (SQLException exception){
		throw exception;
	}
	}


		
	
}
