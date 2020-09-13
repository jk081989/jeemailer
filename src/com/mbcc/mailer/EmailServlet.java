package com.mbcc.mailer;

import javax.servlet.http.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;



import java.io.PrintWriter;

@SuppressWarnings("serial")
public class EmailServlet extends HttpServlet {

	DataSourceDao dao = new MySQLDAO();

	static HashMap<Character, Character> mapper = new HashMap<Character, Character>();

	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
		response.setContentType("application.json");

		ArrayList<EmailDTO> retrievedEmails = dao.getEmails();

		Gson gson = new Gson();
		String jsonEmails = gson.toJson(retrievedEmails);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		response.getWriter().append(jsonEmails);

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		EmailDTO email = new EmailDTO(request.getParameter("subject"), request.getParameter("email_text"),
				request.getParameter("recipient_category"));

		boolean success = false;

		success = dao.insertEmail(email);

		PrintWriter out = response.getWriter();

		String message = "Received your email data and successfully inserted in the database";

		if (!success)
			message = "insert failed";

		out.println(message);

	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++)
			System.out.println("EmailServlet.java: " + System.currentTimeMillis());

	}

}
