package data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Log;

public class LogsDAO extends BaseDAO{

	public void insert(Log log) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO logs(level,stacktrace,fecha)"
					+ "  VALUES(?,?,?)");
			pst.setString(1, log.getLevel());
			pst.setString(2, log.getStack());
			pst.setDate(3, (Date) log.getFecha());
			pst.executeUpdate();
			this.closeConnection(pst);
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
}
