package logic;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import data.ReservaDAO;
import model.Libro;
import model.LibroReserva;
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
	
	public void entregarReserva(Reserva res) throws SQLException, BusinessLogicException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			if (sdf.format(res.getFechaReserva()).equals(sdf.format(date))) {
				this._ReservaDAO.entregarReserva(res);
			}
			else throw new BusinessLogicException("Las reservas se retiran únicamente el día de la fecha pactada. Sin excepción");
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
	
	public ArrayList<String> getFechasDisponible(ArrayList<ArrayList<String>> fechasPorLibro, int cantMeses) {
		LocalDate date = LocalDate.now();
		ArrayList<String> fechas = new ArrayList<String>();
		ArrayList<String> fechasAux = new ArrayList<String>();
		while (date.isBefore(LocalDate.now().plusMonths(cantMeses))) {
			fechas.add('"'+date.toString()+'"');
			date=date.plusDays(1);
		}
		for (ArrayList<String> fechasLibro : fechasPorLibro) {
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
	
	public int getDiasMaximoPrestamo(Libro lib, int diasPoliticaPrestamo, Reserva reserva) throws SQLException {
		try {
			return this._ReservaDAO.getDiasMaximoPrestamo(lib, diasPoliticaPrestamo, reserva);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public int getDiasMaximoPrestamo(Reserva reserva) throws SQLException {
		int diasMaximoLibro;
		int diasMaximoPrestamo;
		int diasPoliticaPrestamo;
		try {
			PoliticaPrestamoLogic ppl = new PoliticaPrestamoLogic();
			diasPoliticaPrestamo = ppl.getActual().getDiasPrestamo();
			diasMaximoPrestamo = diasPoliticaPrestamo;
			for (LibroReserva lr : reserva.getLibros()) {
				diasMaximoLibro = this._ReservaDAO.getDiasMaximoPrestamo(lr.getLibro(), diasMaximoPrestamo, reserva);
				if (diasMaximoLibro < diasMaximoPrestamo) {
					diasMaximoPrestamo = diasMaximoLibro;
				}
			}
			return diasMaximoPrestamo;
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
}