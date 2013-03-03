package com.iarekylew00t.ircbot;

import java.util.*;
import java.io.*;

public class IRCBotMain {
    
    public static void main(String[] args) throws Exception {
        
        Properties p = new Properties();
        p.load(new FileInputStream(new File("./config.ini")));
        
        String server = p.getProperty("Server", "irc.esper.net");
        String channel = p.getProperty("Channel", "#hs_radio,#hs_admin,#hs_rp,#hs_nsfw");
        String nick = p.getProperty("Nick", "Aradiabot");
        String pass = p.getProperty("Password", "");
        
        IRCBot bot = new IRCBot(nick, pass);
        bot.setVerbose(true);
        bot.connect(server);
        bot.joinChannel(channel);
        
    }
    
}