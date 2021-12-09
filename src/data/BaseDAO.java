package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAO {
	protected static Connection conn;

	protected void openConnection() throws SQLException {
		try {
			conn = DbConnector.getInstancia().getConn();
		}
		catch(SQLException e) {
			throw e;
		} 
		catch (ClassNotFoundException e) {
			throw new SQLException("Error en la base de datos. No se encontró el driver");
		}
	}
	
	protected void closeConnection(PreparedStatement pst, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(pst!=null) pst.close();
			if(conn!=null) {
				conn = null;
				DbConnector.getInstancia().releaseConn();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected void closeConnection(Statement stm, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(stm!=null) stm.close();
			if(conn!=null) conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void closeConnection(PreparedStatement pst) {
		try {
			if(pst!=null) pst.close();
			if(conn!=null) conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}