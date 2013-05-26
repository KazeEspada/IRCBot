package com.iarekylew00t.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.iarekylew00t.ircbot.LogHandler;

public class EmailClient {
	private Properties clientProps;
	private Session session;
	private MimeMessage email;
	private InternetAddress toAddress;
	private Transport transport;
	private LogHandler logger = new LogHandler();

	public EmailClient(String username, String password, String host, boolean debug) throws Exception {
		logger.notice("*** SETTING UP EMAIL CLIENT ***");
        clientProps = System.getProperties();
        clientProps.put("mail.smtp.starttls.enable", true);
        clientProps.put("mail.smtp.host", host);
        clientProps.put("mail.smtp.user", username);
        clientProps.put("mail.smtp.password", password);
        clientProps.put("mail.smtp.port", "587");
        clientProps.put("mail.smtp.auth", true);
        clientProps.put("mail.debug", debug);
        session = Session.getInstance(clientProps, new EmailAuthenticator(username, password));
        email = new MimeMessage(session);
        email.setFrom(new InternetAddress(username));
        transport = session.getTransport("smtp");
        transport.connect(host, username, password);
        logger.notice("*** EMAIL CLIENT SETUP SUCCESSFULLY ***");

	}
	
	public void sendEmail(String recipient, String subject, String message) throws MessagingException {
		logger.debug("--- SENDING EMAIL ---");
		toAddress = new InternetAddress(recipient);
    	email.addRecipient(Message.RecipientType.TO, toAddress);
    	email.setSubject(subject);
    	email.setText(message);
        transport.sendMessage(email, email.getAllRecipients());
        logger.debug("--- EMAIL SENT SUCCESSFULLY ---");
	}
	
	public void closeClient() throws MessagingException {
		transport.close();
	}
}