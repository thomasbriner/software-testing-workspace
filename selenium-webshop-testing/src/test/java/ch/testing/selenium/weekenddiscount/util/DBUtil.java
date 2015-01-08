/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 * 
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.testing.selenium.weekenddiscount.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DBUtil. Utility class for reseting the test data.
 * 
 * The user and password should be read from the properties...
 */
public class DBUtil {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String DB_URL = "jdbc:mysql://localhost/broadleaf";
	private static final String DB_URL = "jdbc:mysql://188.166.42.91/broadleaf";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	// Database credentials
	private static final String USER = "webshop";
	private static final String PASS = "webshop2013";

	public static void loadDump() {

		Connection conn = null;
		String q = "";
		try {
			conn = connectToDB();
//			File f = new File(
//					"C:/SWTesting/tools/ResetDB/Dump20140110-with-WeekendDiscount.sql");
			File f = new File(
					"C:/SWTesting/tools/ResetDB/Dump20140110-with-WeekendDiscount_only_dynamic_data.sql");
			BufferedReader bf = new BufferedReader(new FileReader(f));
			String line = null;
			line = bf.readLine();
			while (line != null) {
				q = q + line + "\n";
				line = bf.readLine();
			}
			bf.close();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		// Now we have the content of the dumpfile in 'q'.
		// We must separate the queries, so they can be executed. And Java
		// Simply does this:
		String[] commands = q.split(";\n");

		try {
			Statement statement = conn.createStatement();
			for (String s : commands) {
				s.replaceAll("\n", " ");
				s = s.trim();
				if (s.length() > 0) {
					// System.out.println("Statement: " + s.length() + " "+ s);
					statement.execute(s);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public static void setTestTime(Date date) {

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = connectToDB();
			stmt = conn.createStatement();
			String sql = "UPDATE blc_testing_date_time SET TESTING_DATE = '"
					+ DATE_FORMAT.format(date) + "' where id=1";

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	private static Connection connectToDB() throws SQLException,
			ClassNotFoundException {
		Connection conn = null;
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;

	}

	private static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException se) {
		}// do nothing
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

}
