package com.iarekylew00t.ircbot.listeners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;

import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class ConnectionListener extends ListenerAdapter {
	EmailClient email = DataManager.emailClient;
	LogHandler logger = DataManager.logHandler;
	
	@Override
	public void onDisconnect(DisconnectEvent event) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		Date date = new Date();
    	String curTime = dateFormat.format(date);
    	logger.warning("ARADIABOT DISCONNECTED @ " + curTime);
		email.sendEmail("kyle10468@gmail.com", "NOTICE: Aradiabot Disconnected", "Aradiabot disconnected from the server @ " + curTime);
		System.exit(0);
	}
	
	@Override
	public void onConnect(ConnectEvent event) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		Date date = new Date();
    	String curTime = dateFormat.format(date);
    	logger.warning("ARADIABOT CONNECTED @ " + curTime);
		email.sendEmail("kyle10468@gmail.com", "NOTICE: Aradiabot Connected", "Aradiabot connected to the server @ " + curTime);
	}
}
