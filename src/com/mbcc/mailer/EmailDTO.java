package com.mbcc.mailer;

public class EmailDTO {

	private String subject;
	private String emailText;
	private String recipientCategory;
	private String testerEmail;

	public EmailDTO(String subject, String emailText, String recipientCategory) {

		if (subject == null)
			throw new IllegalArgumentException("subject cannot be null");

		this.subject = subject;
		this.emailText = emailText;

		if (recipientCategory == null)
			throw new IllegalArgumentException("Category cannot be null");

		this.recipientCategory = recipientCategory;
		//this.testerEmail = testerEmail;
	}

	public String getSubject() {
		return subject;
	}

	public String getEmailText() {
		return emailText;
	}

	public String getRecipientCategory() {
		return recipientCategory;
	}

	public String getTesterEmail() {
		return testerEmail;
	}

}
