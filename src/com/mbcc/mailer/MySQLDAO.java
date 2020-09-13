package com.mbcc.mailer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class MySQLDAO implements DataSourceDao {

	/*
	 * Some of the clean ups we can do to this code 1. Pull out the url into the
	 * constants file 2. Create a properties file and put username and password in
	 * that 3. The connection and statement creations which are repeating can be
	 * pulled out into a separate method 4. Anything else you can think off?
	 */

	public boolean insertEmail(EmailDTO email) {

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//String url = "jdbc:mysql://localhost:3306/sakila";

		Properties info = new Properties();
		//info.put("user", "root");
		//info.put("password", "12345");
		//info.put("serverTimezone", "UTC");

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila","root","12345");
			System.out.println("test1");

			stmt = conn.createStatement();
			
			
			String dbString = "insert into Email (email_text, recipient_category, subject) values ('";
			dbString += email.getEmailText();
			dbString += "', '";
			dbString += email.getRecipientCategory();
			dbString += "', '";
			dbString += email.getSubject();
			dbString += "')";

			stmt.executeUpdate(dbString);

			return true;

		}

		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			ex.printStackTrace();
			return false;

		} finally {
			if (stmt != null)
				try {
					stmt.close();

					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public ArrayList<EmailDTO> getEmails() {

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/sakila?user=root&password=12345");

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from sakila.email");
			//System.out.println("test2");

			ArrayList<EmailDTO> results = new ArrayList<EmailDTO>();

			while (rs.next()) {

				String emailText = rs.getString("email_text");
				String recipientCategory = rs.getString("recipient_category");
				String subject = rs.getString("subject");
				//String testerEmail = rs.getString("tester_email");
				EmailDTO email = new EmailDTO(subject, emailText, recipientCategory);
				results.add(email);
			}
			return results;

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return null;

	}

	public static void main(String[] args) {

		MySQLDAO dao = new MySQLDAO();
		ArrayList<EmailDTO> emails = dao.getEmails();
		System.out.println(emails);
	}

}
