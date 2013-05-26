package com.iarekylew00t.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;

import com.iarekylew00t.ircbot.LogHandler;

public class EmailClient {
	private Properties clientProps;
	private Session session;
	private MimeMessage email;
	private InternetAddress toAddress;
	private static Transport transport;
	private PircBot mainBot;
	private LogHandler logger = new LogHandler();

	public EmailClient(String username, String password, String host, boolean debug, PircBot bot) throws Exception {
		logger.notice("*** SETTING UP EMAIL CLIENT ***");
		mainBot = bot;
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
	
	public void sendEmail(String recipient, String subject, String message) {
		try {
		if (transport.isConnected() == false) {
			transport.connect(clientProps.getProperty("mail.smtp.host"), clientProps.getProperty("mail.smtp.user"), clientProps.getProperty("mail.smtp.password"));
		}
		logger.debug("--- SENDING EMAIL ---");
		toAddress = new InternetAddress(recipient);
    	email.addRecipient(Message.RecipientType.TO, toAddress);
    	email.setSubject(subject);
    	email.setText(message);
        transport.sendMessage(email, email.getAllRecipients());
        logger.debug("--- EMAIL SENT SUCCESSFULLY ---");
		} catch (Exception e) {
			mainBot.sendMessage("#hs_radio", Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not send Email - please notify IAreKyleW00t: http://iarekylew00t.tumblr.com/ask");
		}
	}
	
	public void sendEmail(String recipient, String subject, String message, String channel) {
		try {
		if (transport.isConnected() == false) {
			transport.connect(clientProps.getProperty("mail.smtp.host"), clientProps.getProperty("mail.smtp.user"), clientProps.getProperty("mail.smtp.password"));
		}
		logger.debug("--- SENDING EMAIL ---");
		toAddress = new InternetAddress(recipient);
    	email.addRecipient(Message.RecipientType.TO, toAddress);
    	email.setSubject(subject);
    	email.setText(message);
        transport.sendMessage(email, email.getAllRecipients());
        logger.debug("--- EMAIL SENT SUCCESSFULLY ---");
		} catch (Exception e) {
			mainBot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not send Email - please notify IAreKyleW00t: http://iarekylew00t.tumblr.com/ask");
		}
	}
	
	public void closeClient() throws MessagingException {
		transport.close();
	}
}