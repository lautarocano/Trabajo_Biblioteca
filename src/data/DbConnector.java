package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
	
	private static DbConnector instancia;
	
	/*
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/biblioteca";
	private static String user = "root";
	private static String password = "1111";
	private static String serverTimezone  = "UTC";
	*/	
	
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://lyn7gfxo996yjjco.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/r5wpcujxuwi2fbpn";
	private static String user = "h1xsm87yosp5n4sr";
	private static String password = "ca8ay3d3dl6ef4qm";
	private static String serverTimezone  = "UTC";
	private int conectados=0;
	private Connection conn=null;
	
	private DbConnector() throws ClassNotFoundException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw e;
		}
	}
	
	public static DbConnector getInstancia() throws ClassNotFoundException {
		if (instancia == null) {
			instancia = new DbConnector();
		}
		return instancia;
	}
	
	public Connection getConn() throws SQLException {
		try {
			if(conn==null || conn.isClosed()) {
				Properties props = new Properties();
				props.put("user", user);
				props.put("password", password);
				props.put("serverTimezone", serverTimezone);
				conn = DriverManager.getConnection(url,props);
				conectados=0;
			}
		} catch (SQLException e) {
			throw e;
		}
		conectados++;
		return conn;
	}
	
	public void releaseConn() throws SQLException {
		conectados--;
		try {
			if (conectados<=0) {
				conn.close();
			}
		} catch (SQLException e) {
			throw e;
		}
	}

}
