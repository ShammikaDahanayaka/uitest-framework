package com.wso2telco.test.framework.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.wso2telco.test.framework.configuration.Configuration;
import com.wso2telco.test.framework.configuration.CoreConfigurations;

/**
 * The Class MySQLConnection.
 */
public class MySQLConnection {

	/** The connection. */
	private static Connection connection;
	
	/**
	 * Open db connection.
	 *
	 * @author SulakkhanaW
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private static void openDBConnection() throws ClassNotFoundException, SQLException{
		Configuration config = CoreConfigurations.getCoreConfig();
		String driverName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL driver not found "  + e.getMessage());
			throw e;
		}
		String url = config.getValue("mySqlUrl");
		String username = config.getValue("dbUsername");
		String password = config.getValue("dbPassword");
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.err.println("SQL Connection error : " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Gets the connection.
	 *
	 * @author SulakkhanaW
	 * @return the connection
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		if (connection != null && !connection.isClosed()) {
			return connection;
		}
		openDBConnection();
		return connection;
	}
	
}
