package com.wso2telco.test.framework.db.connection.executers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.wso2telco.test.framework.db.connection.MySQLConnection;
import com.wso2telco.test.framework.db.connection.sql.QueryResult;

// TODO: Auto-generated Javadoc
/**
 * The Class SQLExecuter.
 */
public class SQLExecuter {
	
	/** The date format. */
	private static String dateFormat = "yyyy-MM-dd h:mm:ss";
	
	/** The sdf. */
	private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	
	/**
	 * Gets the query results.
	 *
	 * @author SulakkhanaW
	 * @param Query the query
	 * @param newDateFormat the new date format
	 * @return the query results
	 * @throws Exception the exception
	 */
	public static QueryResult getQueryResults(String Query, String newDateFormat) throws Exception {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(newDateFormat);
		return executeQuery(Query, dateFormatter);
	}
	
	/**
	 * Gets the query results.
	 *
	 * @author SulakkhanaW
	 * @param Query the query
	 * @return the query results
	 * @throws Exception the exception
	 */
	public static QueryResult getQueryResults(String Query) throws Exception {
		return executeQuery(Query, sdf);
	}
	
	/**
	 * Execute query.
	 *
	 * @author SulakkhanaW
	 * @param Query the query
	 * @param dateFormatter the date formatter
	 * @return the query result
	 * @throws Exception the exception
	 */
	private static QueryResult executeQuery(String Query, DateFormat dateFormatter) throws Exception {
		QueryResult queryResult = new QueryResult();
		try {
			Connection connection = MySQLConnection.getConnection();
			Statement statement = null;
			try {
				statement = connection.createStatement();
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery(Query);

					while (resultSet.next()) {

						ResultSetMetaData rsmd = resultSet.getMetaData();
						int columnCount = rsmd.getColumnCount();
						Map<String, String> row = new HashMap<>();
						for (int i = 1; i < columnCount + 1; i++) {
							String name = rsmd.getColumnName(i);
							Object value = resultSet.getObject(name);

							if (value == null) {
								value = "";
							}

							if (value instanceof Date) {
								
								java.util.Date date = resultSet.getTimestamp(name);
										
								value = dateFormatter.format(date);
							}

							row.put(name, value.toString().trim().replace("  ", " "));

						}
						queryResult.enqueRow(row);
					}

				} finally {
					resultSet.close();
				}

			} finally {
				statement.close();
			}
		} catch (Exception e) {
			System.err.println("SQL Connection error : " + e.getMessage());
			throw e;
		}

		return queryResult;
	}

}
