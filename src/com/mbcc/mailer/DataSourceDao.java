package com.mbcc.mailer;

import java.util.ArrayList;

public interface DataSourceDao {
	
	public  boolean insertEmail(EmailDTO email);
	
	public  ArrayList<EmailDTO> getEmails();

}
