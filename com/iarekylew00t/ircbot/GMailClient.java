package com.iarekylew00t.ircbot;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailClient {
	Properties props;
	Session session;
	MimeMessage email;
	InternetAddress toAddress;
	Transport transport;

	public GMailClient(String username, String password, String host) throws Exception {
		System.out.println("----- SETTING UP GMAIL CLIENT -----");
        props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.debug", "true");
        session = Session.getInstance(props, new GMailAuthenticator(username, password));
        email = new MimeMessage(session);
        email.setFrom(new InternetAddress(username));
        transport = session.getTransport("smtp");
        transport.connect(host, username, password);
		System.out.println("----- GMAIL CLIENT SETUP SUCCESSFULLY -----");

	}
	
	public void sendEmail(String recipient, String subject, String message) throws MessagingException {
		System.out.println("----- SENDING EMAIL -----");
		toAddress = new InternetAddress(recipient);
    	email.addRecipient(Message.RecipientType.TO, toAddress);
    	email.setSubject(subject);
    	email.setText(message);
        transport.sendMessage(email, email.getAllRecipients());
		System.out.println("----- EMAIL SENT SUCCESSFULLY -----");
	}
	
	public void closeClient() throws MessagingException {
		transport.close();
	}
}