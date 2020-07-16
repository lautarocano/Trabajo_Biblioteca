package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAO {
	protected static Connection conn;
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/biblioteca";
	private static String user = "root";
	private static String password = "root";
	
	protected void openConnection() throws SQLException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected void closeConnection(PreparedStatement pst, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(pst!=null) pst.close();
			if(conn!=null) conn.close();
		}
		catch (SQLException e) {
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