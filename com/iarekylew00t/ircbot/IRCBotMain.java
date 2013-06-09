package com.iarekylew00t.ircbot;

import java.nio.charset.Charset;

import org.pircbotx.PircBotX;

import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.google.Google;
import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.ircbot.listeners.BasicCommandListener;
import com.iarekylew00t.ircbot.listeners.DisconnectListener;
import com.iarekylew00t.ircbot.listeners.GoogleListener;
import com.iarekylew00t.ircbot.listeners.HomestuckListener;
import com.iarekylew00t.ircbot.listeners.LogListener;
import com.iarekylew00t.ircbot.listeners.MusicListener;
import com.iarekylew00t.ircbot.listeners.PermissionCommandListener;
import com.iarekylew00t.ircbot.listeners.RequestListener;
import com.iarekylew00t.ircbot.listeners.WebCommandListener;
import com.iarekylew00t.managers.DataManager;

public class IRCBotMain {
	private static LogHandler logger = DataManager.logHandler;
	
	public static void main(String[] args) throws Exception{
		logger.info("SETTING UP GOOGLE CLIENT");
		DataManager.google = new Google("AIzaSyCBCyKYkO3zcMrBAVsOkyBr5C0GhoGyDXw#");
        logger.info("GOOGLE CLIENT SETUP SUCCESSFULLY");
		
		EmailClient email = DataManager.emailClient;
		if (!DataManager.emailAddress.isEmpty() || !DataManager.emailPassword.isEmpty()) {
			logger.info("SETTING UP EMAIL CLIENT");
        	email.setupEmail(DataManager.emailAddress, DataManager.emailPassword);
        	logger.info("EMAIL CLIENT SETUP SUCCESSFULLY");
        }
		
        PircBotX bot = DataManager.IRCbot;
        bot.setName(DataManager.nick);
        bot.setLogin("AA");
        bot.setAutoNickChange(true);
        bot.setEncoding(Charset.forName("UTF-8"));
        bot.setVerbose(DataManager.debug);
        bot.getListenerManager().addListener(new BasicCommandListener());
        bot.getListenerManager().addListener(new PermissionCommandListener());
        bot.getListenerManager().addListener(new MusicListener());
        bot.getListenerManager().addListener(new GoogleListener());
        bot.getListenerManager().addListener(new HomestuckListener());
        bot.getListenerManager().addListener(new WebCommandListener());
        bot.getListenerManager().addListener(new DisconnectListener());
        bot.getListenerManager().addListener(new RequestListener());
        bot.getListenerManager().addListener(new LogListener());
        bot.connect(DataManager.server);
        if (!DataManager.nickPassword.isEmpty()) {
        	bot.identify(DataManager.nickPassword);
        }
        bot.joinChannel(DataManager.channel);
	}
}
