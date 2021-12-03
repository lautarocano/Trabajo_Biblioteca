package logic;

import java.sql.SQLException;
import java.time.LocalDate;
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
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Libro getOne(int id) throws SQLException {
		try {
			return this._LibroDAO.getOne(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Libro> getAllByGenero(Genero genero) throws SQLException {
		try {
			return this._LibroDAO.getAllByGenero(genero);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Libro> getAllByTitulo(String titulo) throws SQLException {
		try {
			return this._LibroDAO.getAllByTitulo(titulo);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Libro> getAllByTituloAndGenero(String titulo, Genero genero) throws SQLException {
		try {
			return this._LibroDAO.getAllByTituloAndGenero(titulo, genero);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}

	public void insert(Libro lib) throws SQLException {
		try {
				this._LibroDAO.insert(lib);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void update(Libro lib) throws SQLException {
		try {
			this._LibroDAO.update(lib);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(Libro lib) throws SQLException {
		try {
			this._LibroDAO.delete(lib);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<String> getFechasDisponible(Libro lib, int cantMeses) throws SQLException {
		try {
			return this._LibroDAO.getFechasDisponible(lib, cantMeses);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<String> getFechasDisponible(ArrayList<Libro> libros, int cantMeses) throws SQLException {
		LocalDate date = LocalDate.now();
		ArrayList<String> fechas = new ArrayList<String>();
		ArrayList<String> fechasAux = new ArrayList<String>();
		ArrayList<String> fechasLibro = new ArrayList<String>();
		try {
			while (date.isBefore(LocalDate.now().plusMonths(cantMeses))) {
				fechas.add('"'+date.toString()+'"');
				date=date.plusDays(1);
			}
			for (Libro l : libros) {
				fechasLibro = this._LibroDAO.getFechasDisponible(l, cantMeses);
				for (String fecha : fechas) {
					System.out.print(fecha);
					if (!fechasLibro.contains(fecha)) {
						fechasAux.add(fecha);
						System.out.print("No disponible");
					}
				}
				System.out.println("Fin");
			}
			fechas.removeAll(fechasAux);
			return fechas;
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
}