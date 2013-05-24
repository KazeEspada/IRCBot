package com.iarekylew00t.ircbot;

import java.util.*;
import java.io.*;

public class IRCBotMain {
	public static IRCBot bot;
	private static String email;
	private static String emailpass;
    
    public static void main(String[] args) throws Exception {
    	setupBot();
    }
    
    public static void setupBot() throws Exception {        
        Properties p = new Properties();
        p.load(new FileInputStream(new File("./config.ini")));
        
        String server = p.getProperty("Server", "irc.esper.net");
        String channel = p.getProperty("Channel", "#hs_radio,#hs_radio2,#hs_radio3,#hs_radio4,#hs_admin");
        String nick = p.getProperty("Nick", "Aradiabot");
        String pass = p.getProperty("Password", "");
        email = p.getProperty("Email", "example@gmail.com");
        emailpass = p.getProperty("EmailPassword", "abc123");
        
        bot = new IRCBot(nick, pass);
        bot.setVerbose(true);
        bot.connect(server);
        bot.joinChannel(channel);
    }
    
    static String getEmail() {
    	return email;
    }
    
    static String getEmailPass() {
    	return emailpass;
    }
}