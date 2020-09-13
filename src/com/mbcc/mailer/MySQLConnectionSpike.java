package com.mbcc.mailer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MySQLConnectionSpike {

	static Connection conn = null;

	public static void main(String[] args) {

		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/mailer?user=root&password=Password&serverTimezone=UTC");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
					"insert into Email (email_text, recipient_category, subject) values (\"test2 email text\", \"test2 category\","
							+ " \"test2 subject\")\n");

			ResultSet rs = stmt.executeQuery("SELECT * FROM Email");

			while (rs.next()) {
				System.out.println(rs.getString("email_text"));
				System.out.println(rs.getString("recipient_category"));
				System.out.println(rs.getString("subject"));
			}

			// Do something with the Connection
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

}
