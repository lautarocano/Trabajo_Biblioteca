package logic;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import data.ReservaDAO;
import model.Libro;
import model.Reserva;

public class ReservaLogic {
private ReservaDAO _ReservaDAO;
	
	public ReservaLogic() {
		this._ReservaDAO = new ReservaDAO();
	}
	
	public ArrayList<Reserva> getAll() throws SQLException {
		try {
			return this._ReservaDAO.getAll();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Reserva> getAllPendientes() throws SQLException {
		try {
			return this._ReservaDAO.getAllPendientes();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Reserva> getAllBySocio(int id) throws SQLException {
		try {
			return this._ReservaDAO.getAllBySocio(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Reserva> getAllPendientesBySocio(int id) throws SQLException {
		try {
			return this._ReservaDAO.getAllPendientesBySocio(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Reserva getOne(int id) throws SQLException {
		try {
			return this._ReservaDAO.getOne(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(Reserva res) throws SQLException {
		try {
			this._ReservaDAO.delete(res);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void entregarReserva(Reserva res) throws SQLException {
		try {
			this._ReservaDAO.entregarReserva(res);;
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<Reserva> getReservasFuturas(int idLibro) throws SQLException {
		try {
			return this._ReservaDAO.getReservasFuturas(idLibro);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public ArrayList<String> getFechasDisponible(Libro lib, int cantMeses) throws SQLException {
		try {
			return this._ReservaDAO.getFechasDisponible(lib, cantMeses);
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
				fechasLibro = this._ReservaDAO.getFechasDisponible(l, cantMeses);
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