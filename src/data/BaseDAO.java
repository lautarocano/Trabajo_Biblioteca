package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public abstract class BaseDAO {
	protected static Connection conn;
	
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/biblioteca";
	private static String user = "root";
	private static String password = "1111";
	private static String serverTimezone  = "UTC";
	
	
	/*
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://lyn7gfxo996yjjco.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/r5wpcujxuwi2fbpn";
	private static String user = "h1xsm87yosp5n4sr";
	private static String password = "ca8ay3d3dl6ef4qm";
	private static String serverTimezone  = "UTC";
	*/
	protected void openConnection() throws SQLException {
		try {
			Class.forName(driver);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", password);
			props.put("serverTimezone", serverTimezone);
			conn = DriverManager.getConnection(url,props);
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