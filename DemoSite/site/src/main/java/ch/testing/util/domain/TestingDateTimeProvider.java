package ch.testing.util.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class TestingDateTimeProvider {

	private static TestingDateTimeProvider instance = null;

	private TestingDateTimeProvider() {
	}

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public static TestingDateTimeProvider getInstance() {
		if (instance == null) {
			instance = new TestingDateTimeProvider();
		}
		return instance;
	}

	public Date getTestingDate() throws Exception {
		try {
			Timestamp result = null;
			
			String databaseDriver = System.getProperty("database.driver");
			String databaseUser = System.getProperty("database.user");
			String databasePass = System.getProperty("database.password");
			String databaseUrl = System.getProperty("database.url");

			
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(databaseDriver);
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection(databaseUrl+"?"
							+ "user="+databaseUser+"&password="+databasePass);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement
					.executeQuery("select * from BLC_TESTING_DATE_TIME");
			while (resultSet.next()) {
				 result = resultSet.getTimestamp("TESTING_DATE");
			}
			return new Date(result.getTime());

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
