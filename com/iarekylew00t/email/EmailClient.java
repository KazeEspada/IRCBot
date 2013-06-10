package com.iarekylew00t.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class EmailClient {
	private static Properties props;
	private static Session session;
	private static MimeMessage email;
	private static InternetAddress toAddress;
	private static Transport transport;
	private static LogHandler logger = DataManager.logHandler;

	public EmailClient() {}
	
	public void setupEmail(String emailAddress, String emailPassword) throws Exception {
        props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", emailAddress);
        props.put("mail.smtp.password", emailPassword);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        logger.debug("host=" + props.getProperty("mail.smtp.host") + " ; login=" + emailAddress + " ; emailPassword=" + emailPassword);
        session = Session.getInstance(props, new EmailAuthenticator(emailAddress, emailPassword));
        email = new MimeMessage(session);
        email.setFrom(new InternetAddress(emailAddress));
        transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", emailAddress, emailPassword);
	}

	public void sendEmail(String recipient, String subject, String message) {
		try {
			connectTransport();
			toAddress = new InternetAddress(recipient);
	    	email.addRecipient(Message.RecipientType.TO, toAddress);
	    	email.setSubject(subject);
	    	email.setText(message);
	    	logger.debug("recipient=" + recipient);
	    	logger.debug("subject=" + subject);
	    	logger.debug("message=" + message);
	        transport.sendMessage(email, email.getAllRecipients());
		} catch (Exception e) {
			DataManager.logHandler.error("COULD NOT SEND EMAIL", e);
		}
	}
	
	public void addRecipient(InternetAddress recipient) throws Exception {
		email.addRecipient(Message.RecipientType.TO, recipient);
	}
	
	public boolean isConnected() {
		if (transport.isConnected()) {
			logger.debug("isConnected()=true");
			return true;
		}
		logger.debug("isConnected()=false");
		return false;
	}
	
	public void connectTransport() throws Exception {
		if (!isConnected()) {
			transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
		}
	}
}