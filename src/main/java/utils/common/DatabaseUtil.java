package utils.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {

	private static Connection connection = null;
	private static Statement stmt;
	private static ResultSet rs;

	public Connection connectToDB(String connectionURL, String schema, String UID, String PSWD) {
		try {
			if (connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + connectionURL + ":3306/" + schema, UID,
						PSWD);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static List<String> executeQuery(String sqlQuery) {
		List<String> dbOpt = new ArrayList<String>();

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				ResultSetMetaData rsM = rs.getMetaData();

				for (int col = 1; col <= rsM.getColumnCount(); col++) {
					dbOpt.add(rs.getString(col));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbOpt;
	}

	public void closeDBConnection() {
		try {
			if (!connection.isClosed()) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
