package com.iarekylew00t.ircbot.listeners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.ReconnectEvent;

import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class DisconnectListener extends ListenerAdapter {
	EmailClient email = DataManager.emailClient;
	LogHandler logger = DataManager.logHandler;
	
	@Override
	public void onDisconnect(DisconnectEvent event) {
		try {
	    	Thread.sleep(2500);
	    	event.getBot().reconnect();
		} catch (Exception e) {
			e.printStackTrace();
			DataManager.exception = e;
		}
	}
	
	@Override
	public void onReconnect(ReconnectEvent event) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		Date date = new Date();
    	String curTime = dateFormat.format(date);
        event.getBot().startIdentServer();
        if (!DataManager.nickPassword.isEmpty()) {
        	event.getBot().identify(DataManager.nickPassword);
        }
        event.getBot().joinChannel(DataManager.channel);
    	email.sendEmail("kyle10468@gmail.com", "NOTICE: Aradiabot Reconnect", "Aradiabot successfully reconnected to the server @ " + curTime);
	}

}
