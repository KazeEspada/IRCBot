package com.iarekylew00t.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAuthenticator extends Authenticator {
	String user;
	String pw;
	
	public EmailAuthenticator (String username, String password) {
		super();
		this.user = username;
		this.pw = password;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pw);
	}
}
