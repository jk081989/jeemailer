package com.mbcc.mailer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DerbyDAO implements DataSourceDao{
	
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
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		try {
			conn = DriverManager.getConnection("jdbc:derby:mailer;create=true");

			stmt = conn.createStatement();
			//conn.createStatement().execute("create table email(email_text varchar(100),recipient_category varchar(20),subject varchar(100))");

			String dbString = "insert into Email (email_text, recipient_category, subject) values ('";
			dbString += email.getEmailText();
			dbString += "', '";
			dbString += email.getRecipientCategory();
			dbString += "', '";
			dbString += email.getSubject();
			dbString += "')";

			
			ResultSet myres = stmt.executeQuery("select * from Email");
			while(myres.next())
			{
			System.out.println(myres.getString("email_text") + " , " +myres.getString("recipient_category"));	
			}
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
					.getConnection("jdbc:mysql://localhost/mailer");

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Email");

			ArrayList<EmailDTO> results = new ArrayList<EmailDTO>();

			while (rs.next()) {

				String emailText = rs.getString("email_text");
				String recipientCategory = rs.getString("recipient_category");
				String subject = rs.getString("subject");
				String testerEmail = rs.getString("tester_email");
				EmailDTO email = new EmailDTO(subject, emailText, recipientCategory);
				results.add(email);
			}
			return results;

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return null;

	}
	
	
	// TODO call this createTable only once when one of the servlet is initialized, call this createTable
	
	public void createTable() throws SQLException {
		
		String emailTableSql = "CREATE TABLE email (" + 
				"  id int NOT NULL GENERATED ALWAYS AS IDENTITY," + 
				"  email_text blob NOT NULL," + 
				"  recipient_category varchar(255)  NOT NULL," + 
				"  subject varchar(255)  NOT NULL," + 
				"  tester_email varchar(255) DEFAULT NULL," + 
				"  email_date timestamp DEFAULT NULL," + 
				"  PRIMARY KEY (id)" + 
				")";
		
		Connection conn = DriverManager.getConnection("jdbc:derby:mailer;create=true");
		Statement stmt = conn.createStatement();
		stmt.execute(emailTableSql);
		
	}

	public static void main(String[] args) throws SQLException {

		DerbyDAO dao = new DerbyDAO();
		dao.createTable();
	}


}
