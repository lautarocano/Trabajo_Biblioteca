package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.ReservaDAO;
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
	
	public ArrayList<Reserva> getAllBySocio(int id) throws SQLException {
		try {
			return this._ReservaDAO.getAllBySocio(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
}
