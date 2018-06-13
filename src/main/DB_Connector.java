package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DB_Connector {
	
	private static String connectionString = "jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=false";

	static final String USER = "root";
	static final String PASS = "Aa123456!";

	public static ArrayList<User> readTable() {
		ArrayList<User> users  = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connectionString, USER, PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM users.students;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String name = rs.getString("name");
				String password =rs.getString("password");
				String email = rs.getString("email");
				users.add(new User(name,password,email));
			
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return users;
	}
	
	public static void insertNewUser(String name, String password, String email) {
		Connection conn = null;
		PreparedStatement  preparedStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connectionString, USER, PASS);
			
			preparedStatement = conn.prepareStatement("INSERT INTO users.students (name, password, email) VALUES (?, ?, ?)");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, email);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException se2) {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
}
