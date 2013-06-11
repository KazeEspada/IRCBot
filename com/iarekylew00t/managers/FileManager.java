package com.iarekylew00t.managers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.iarekylew00t.helpers.Downloader;
import com.iarekylew00t.helpers.FileHelper;
import com.iarekylew00t.ircbot.handlers.LogHandler;

public final class FileManager {
	private static String endFile = "-quotes.txt";
	private static String FILE_BASE = "https://raw.github.com/IAreKyleW00t/IRCBot/master/files/";
	private static File _ROOT = new File("./files/");
	private static File _QUOTE = new File("./files/quotes/");
	private static File HS_LINKS = new File("./files/hs_links.txt");
	private static File[] quotes = {
		 new File("./files/quotes/aradia" + endFile),
		 new File("./files/quotes/aranea" + endFile),
		 new File("./files/quotes/arquiusprite" + endFile),
		 new File("./files/quotes/caliborn" + endFile),
		 new File("./files/quotes/calliope" + endFile),
		 new File("./files/quotes/calsprite" + endFile),
		 new File("./files/quotes/cronus" + endFile),
		 new File("./files/quotes/damara" + endFile),
		 new File("./files/quotes/dave" + endFile),
		 new File("./files/quotes/davesprite" + endFile),
		 new File("./files/quotes/dirk" + endFile),
		 new File("./files/quotes/doc" + endFile),
		 new File("./files/quotes/dragonsprite" + endFile),
		 new File("./files/quotes/equius" + endFile),
		 new File("./files/quotes/eridan" + endFile),
		 new File("./files/quotes/erisolsprite" + endFile),
		 new File("./files/quotes/feferi" + endFile),
		 new File("./files/quotes/fefetasprite" + endFile),
		 new File("./files/quotes/gamzee" + endFile),
		 new File("./files/quotes/hic" + endFile),
		 new File("./files/quotes/horuss" + endFile),
		 new File("./files/quotes/hussie" + endFile),
		 new File("./files/quotes/jade" + endFile),
		 new File("./files/quotes/jadesprite" + endFile),
		 new File("./files/quotes/jake" + endFile),
		 new File("./files/quotes/jane" + endFile),
		 new File("./files/quotes/jaspersprite" + endFile),
		 new File("./files/quotes/john" + endFile),
		 new File("./files/quotes/kanaya" + endFile),
		 new File("./files/quotes/kankri" + endFile),
		 new File("./files/quotes/karkat" + endFile),
		 new File("./files/quotes/kurloz" + endFile),
		 new File("./files/quotes/latula" + endFile),
		 new File("./files/quotes/meenah" + endFile),
		 new File("./files/quotes/meulin" + endFile),
		 new File("./files/quotes/mituna" + endFile),
		 new File("./files/quotes/nannasprite" + endFile),
		 new File("./files/quotes/nepeta" + endFile),
		 new File("./files/quotes/porrim" + endFile),
		 new File("./files/quotes/rose" + endFile),
		 new File("./files/quotes/roxy" + endFile),
		 new File("./files/quotes/rufioh" + endFile),
		 new File("./files/quotes/sollux" + endFile),
		 new File("./files/quotes/squarewave" + endFile),
		 new File("./files/quotes/tavrisprite" + endFile),
		 new File("./files/quotes/tavros" + endFile),
		 new File("./files/quotes/terezi" + endFile),
		 new File("./files/quotes/vriska" + endFile)};
	private static LogHandler logger = DataManager.logHandler;
    
    public static void checkFiles() throws MalformedURLException {
    	if (!_QUOTE.exists()) {
    		_QUOTE.mkdirs();
    	}
    	if (!_ROOT.exists()) {
    		_ROOT.mkdirs();
    	}
    	if (!HS_LINKS.exists()) {
			Downloader.downloadFile(new URL(FILE_BASE + HS_LINKS.getName()), HS_LINKS);
    	}
    	for (File file : quotes) {
    		if(!file.exists()) {
    			Downloader.downloadFile(new URL(FILE_BASE + "/quotes/" + file.getName()), file);
    		}
    	}
    }
    
    public static void updateConfig() {
    	FileHelper.writeToFile(DataManager.CONFIG, "#======================================================\n" +
		   	   "#===   DataManager.CONFIGuration File for Aradiabot (Rev. 1A)   ===\n" +
			   "#======================================================\n", false);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "# The Version of your bot\n" +
    			"Version = " + DataManager.VER + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#The Nickname for your bot\n" +
    			"Nick = " + DataManager.nick + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#Your NickServ Password\n" +
    			"#LEAVE BLANK FOR NO PASSWORD\n" +
    			"Password = " + DataManager.nickPassword + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#Your Login Name\n" +
    			"Login = " + DataManager.login + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#Server to connect to\n" +
    			"Server = " + DataManager.server + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#Channel(s) to connect to\n" +
    			"Channel = " + DataManager.channel + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#The Email Address to use for the Email Client\n" +
    			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
    			"Email = " + DataManager.emailAddress + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#The Email Password for the Email Client\n" +
    			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
    			"EmailPassword = " + DataManager.emailPassword + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#Enable or Disable Debugging\n" +
    			"#Default: true\n" +
    			"Debug = " + DataManager.debug + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#Enable to disable Password Encryption\n" +
    			"#Default: false\n" +
    			"#NOTE: This will encrypt your NickServ and Email Address password (regardless if they're blank or not).\n" +
    			"#It will still encrypt the blank space. DON'T TOUCH IT. It won't activate anything since it'll still\n" +
    			"#decrypt to an empty space like before.\n" +
    			"Encrypt = " + DataManager.encrypt + "\n", true);
    	
    	FileHelper.writeToFile(DataManager.CONFIG, "#Securely Generated Salt\n" +
     			"#NOTE: This is generated the first time you run the bot\n" +
    			"#DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU'RE DOING (Min. 24 characters)\n" +
    			"Salt = " + DataManager.salt, true);
    }
    
    public static void createDefaultConfig() {
    	logger.notice("NO DataManager.CONFIGURATION FOUND - CREATING DEFAULT");
    	FileHelper.writeToFile(DataManager.CONFIG, "#======================================================\n" +
 		   	   "#===   DataManager.CONFIGuration File for Aradiabot (Rev. 1A)   ===\n" +
 			   "#======================================================\n", false);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "# The Version of your bot\n" +
     			"Version = 1.0.0.0\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#The Nickname for your bot\n" +
     			"Nick = Aradiabot\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#Your NickServ Password\n" +
     			"#LEAVE BLANK FOR NO PASSWORD\n" +
     			"Password =\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#Your Login Name\n" +
     			"Login = AA\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#Server to connect to\n" +
     			"Server = irc.esper.net\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#Channel(s) to connect to\n" +
     			"Channel = #channel1,#channel2,#channel3\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#The Email Address to use for the Email Client\n" +
     			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
     			"Email =\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#The Email Password for the Email Client\n" +
     			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
     			"EmailPassword =\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#Enable or Disable Debugging\n" +
     			"#Default: false\n" +
     			"Debug = false\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#Enable to disable Password Encryption\n" +
     			"#Default: false\n" +
     			"#NOTE: This will encrypt your NickServ and Email Address password (regardless if they're blank or not).\n" +
     			"#It will still encrypt the blank space. DON'T TOUCH IT. It won't activate anything since it'll still\n" +
     			"#decrypt to an empty space like before.\n" +
     			"Encrypt = false\n", true);
     	
     	FileHelper.writeToFile(DataManager.CONFIG, "#Securely Generated Salt\n" +
     			"#NOTE: This is generated the first time you run the bot\n" +
     			"#DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU'RE DOING\n" +
     			"Salt =",true);
    }
}
