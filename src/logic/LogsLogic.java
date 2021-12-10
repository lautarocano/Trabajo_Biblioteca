package logic;

import java.sql.SQLException;

import data.LogsDAO;
import model.Log;

public class LogsLogic {

	private LogsDAO _LogsDAO;

	public void insert(Log logs) throws SQLException {
		try {
				this._LogsDAO.insert(logs);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
}
