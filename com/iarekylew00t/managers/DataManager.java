package com.iarekylew00t.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.encryption.ByteGenerator;
import com.iarekylew00t.encryption.Encoder;
import com.iarekylew00t.encryption.Encryptor;
import com.iarekylew00t.google.Google;
import com.iarekylew00t.helpers.FileHelper;
import com.iarekylew00t.ircbot.IRCBot;
import com.iarekylew00t.ircbot.handlers.LogHandler;

public enum DataManager {
	INSTANCE;
	public static LogHandler logHandler = new LogHandler();
	public static EmailClient emailClient = new EmailClient();
	public static PircBotX IRCbot = new IRCBot();
	public static Google google;
	public static Properties props = new Properties();
	public static Map<String, String> commandList = new HashMap<>();
	public static String VER, server, channel, nick, nickPassword, login, emailAddress, emailPassword, salt;
	public static boolean debug, encrypt;
	public static int backups;
	public static Exception exception;
	public static File CONFIG = new File("config.ini");
	public static File ENCRYPT_FILE = new File("./files/.encrypted");
	public static String ERROR = Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL;
	
    static {
        if (!CONFIG.exists()) {
        	FileHelper.createFile(CONFIG);
        	createDefaultConfig();
        } else {
			try {
				props.load(new FileInputStream(CONFIG));
			} catch (IOException e) {
				e.printStackTrace();
				exception = e;
			}
        }
        VER = props.getProperty("Version", "1.0.0.0");
		server = props.getProperty("Server", "irc.esper.net");
		channel = props.getProperty("Channel", "#channel1,#channel2,#channel3");
		nick = props.getProperty("Nick", "Aradiabot");
		nickPassword = props.getProperty("Password", "");
		login = props.getProperty("Login", "AA");
		emailAddress = props.getProperty("Email", "");
		emailPassword = props.getProperty("EmailPassword", "");
		salt = props.getProperty("Salt", "");
		try {
			backups = Integer.parseInt(props.getProperty("Backups", "10"));
		} catch (Exception e) {
			logHandler.error("COULD NOT PARSE INTEGER VALUE", e);
		}
		try {
			debug = Boolean.parseBoolean(props.getProperty("Debug", "false"));
			logHandler.enableDebug(debug);
			encrypt = Boolean.parseBoolean(props.getProperty("Encrypt", "false"));
		} catch (Exception e) {
			logHandler.error("COULD NOT PARSE BOOLEAN VALUE - ONLY USE \"TRUE\" or \"FALSE\"", e);
		}
		
		if (encrypt) {
			if (ENCRYPT_FILE.exists()) {
				try {
					nickPassword = Encoder.decodeBase64(Encryptor.decryptBlowfish(Encryptor.decryptAES(nickPassword)));
					emailPassword = Encoder.decodeBase64(Encryptor.decryptBlowfish(Encryptor.decryptAES(emailPassword)));
				} catch (Exception e) {
					logHandler.error("COULD NOT DECRYPT PASSWORDS", e);
				}
			} else if (!ENCRYPT_FILE.exists()) {
				try {
					logHandler.notice("*** ENCRYPTING PASSWORDS ***");
					nickPassword = Encryptor.encryptAES(Encryptor.encryptBlowfish(Encoder.encodeBase64(nickPassword)));
					emailPassword = Encryptor.encryptAES(Encryptor.encryptBlowfish(Encoder.encodeBase64(emailPassword)));
				} catch (Exception e) {
					logHandler.error("COULD NOT ENCRYPT PASSWORDS", e);
				}
				logHandler.notice("*** UPDATING CONFIG ***");
				updateConfig();
	    		FileHelper.createFile(ENCRYPT_FILE);
				try {
					nickPassword = Encoder.decodeBase64(Encryptor.decryptBlowfish(Encryptor.decryptAES(nickPassword)));
					emailPassword = Encoder.decodeBase64(Encryptor.decryptBlowfish(Encryptor.decryptAES(emailPassword)));
				} catch (Exception e) {
					logHandler.error("COULD NOT DECRYPT PASSWORDS", e);
				}
			}
		} else if (!encrypt) {
			if (ENCRYPT_FILE.exists()) {
				try {
					logHandler.notice("*** DECRYPTING PASSWORDS ***");
					nickPassword = Encoder.decodeBase64(Encryptor.decryptBlowfish(Encryptor.decryptAES(nickPassword)));
					emailPassword = Encoder.decodeBase64(Encryptor.decryptBlowfish(Encryptor.decryptAES(emailPassword)));
				} catch (Exception e) {
					logHandler.error("COULD NOT DECRYPT PASSWORDS", e);
				}
				logHandler.notice("*** UPDATING CONFIG ***");
				updateConfig();
	    		ENCRYPT_FILE.delete();
			}
		}
		if (salt.isEmpty()) {
			salt = ByteGenerator.toHex(ByteGenerator.genRandomByte(12));
			updateConfig();
		}
		
		setupCommandList();
    }
    
    private static void setupCommandList() {
    	commandList.put("8ball", "Usage: $8ball <question>");
    	commandList.put("announce", "OP ONLY - Usage: $announce <message>");
    	commandList.put("commands", "Usage: $commands");
    	commandList.put("decrypt", "Usage: $decrypt <AES|DES|3DES|Blowfish|RC2> <encodedData>");
    	commandList.put("decode", "Usage: $decode <BASE64> <encodedData>");
    	commandList.put("define", "Usage: $define <word>");
    	commandList.put("date", "Usage: $date");
    	commandList.put("error", "Usage: $error");
    	commandList.put("encrypt", "Usage: $encrypt <AES|DES|3DES|Blowfish|RC2> <data>");
    	commandList.put("encode", "Usage: $encode <BASE64> <data>");
    	commandList.put("expand", "Usage: $expand <goo.gl URL>");
    	commandList.put("faq", "Usage: $faq");
    	commandList.put("gearup", "Usage: $gearup");
    	commandList.put("google", "Usage: $google <search> || $g <search>");
    	commandList.put("hash", "Usage: $hash <MD5|MD2|SHA-1|SHA-256|SHA-348|SHA-512> <data>");
    	commandList.put("heal", "Usage: $heal <user>");
    	commandList.put("help", "Usage: $help <command> (<required> [optional])");
    	commandList.put("irc", "Usage: $irc");
    	commandList.put("kill", "Usage: $kill <user>");
    	commandList.put("latest", "Usage: $latest");
    	commandList.put("lmtyahs", "Usage: $lmtyahs");
    	commandList.put("mspa", "Usage: $mspa");
    	commandList.put("mspawiki", "Usage: $mspawiki <search>");
    	commandList.put("page", "Usage: $page || $page [number]");
    	commandList.put("pap", "Usage: $pap <user>");
    	commandList.put("ping", "Usage: $ping");
    	commandList.put("playflute", "Usage: $playflute");
    	commandList.put("poke", "Usage: $poke <user>");
    	commandList.put("prevsong", "Usage: $prevsong || $prev");
    	commandList.put("quote", "Usage: $quote <character> [number]");
    	commandList.put("reboot", "OP ONLY - Usage: $reboot");
    	commandList.put("radio", "Usage: $radio");
    	commandList.put("random", "Usage: $random [maxNum]");
    	commandList.put("req", "Usage: $req [args] || $req [songname]");
    	commandList.put("restart", "VOICE/OP ONLY - Usage: $restart");
    	commandList.put("revive", "Usage: $revive <user>");
    	commandList.put("rules", "Usage: $rules");
    	commandList.put("search", "Usage: $search <homestuck page>");
    	commandList.put("serve", "Usage: $serve <something>");
    	commandList.put("shoosh", "Usage: $shoosh <user>");
    	commandList.put("shooshpap", "Usage: $shooshpap <user>");
    	commandList.put("shoot", "Usage: $shoot <user>");
    	commandList.put("shorten", "Usage: $shorten <longURL>");
    	commandList.put("shout", "VOICE/OP ONLY - Usage: $shout <message>");
    	commandList.put("slap", "Usage: $slap <user>");
    	commandList.put("song", "Usage: $song || $cursong");
    	commandList.put("songlist", "Usage: $songlist");
    	commandList.put("source", "Usage: $source || $github");
    	commandList.put("srandom", "Usage: $srandom [bytes]");
    	commandList.put("stab", "Usage: $stab <user>");
    	commandList.put("talk", "Usage: $talk <message>");
    	commandList.put("tellkyle", "VOICE/OP ONLY - Usage: $tellkyle <message>");
    	commandList.put("time", "Usage: $time");
    	commandList.put("udefine", "Usage: $udefine <word>");
    	commandList.put("update", "Usage: $update");
    	commandList.put("ver", "Usage: $ver");
    	commandList.put("weather", "Usage: $weather <zipcode> || $we <zipcode>");
    	commandList.put("wiki", "Usage: $wiki <search>");
    	commandList.put("youtube", "Usage: $youtube <search> || $yt <search>");
    }
    
    private static void updateConfig() {
    	FileHelper.writeToFile(CONFIG, "#======================================================\n" +
		   	   "#===   Configuration File for Aradiabot (Rev. 1A)   ===\n" +
			   "#======================================================\n", false);
    	
    	FileHelper.writeToFile(CONFIG, "# The Version of your bot\n" +
    			"Version = " + VER + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#The Nickname for your bot\n" +
    			"Nick = " + nick + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#Your NickServ Password\n" +
    			"#LEAVE BLANK FOR NO PASSWORD\n" +
    			"Password = " + nickPassword + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#Your Login Name\n" +
    			"Login = " + login + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#Server to connect to\n" +
    			"Server = " + server + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#Channel(s) to connect to\n" +
    			"Channel = " + channel + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#The Email Address to use for the Email Client\n" +
    			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
    			"Email = " + emailAddress + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#The Email Password for the Email Client\n" +
    			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
    			"EmailPassword = " + emailPassword + "\n", true);

    	FileHelper.writeToFile(CONFIG, "#Number of Backup Logs to keep\n" +
    			"#Default: 10\n" +
    			"Backups = " + backups + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#Enable or Disable Debugging\n" +
    			"#Default: true\n" +
    			"Debug = " + debug + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#Enable to disable Password Encryption\n" +
    			"#Default: false\n" +
    			"#NOTE: This will encrypt your NickServ and Email Address password (regardless if they're blank or not).\n" +
    			"#It will still encrypt the blank space. DON'T TOUCH IT. It won't activate anything since it'll still\n" +
    			"#decrypt to an empty space like before.\n" +
    			"Encrypt = " + encrypt + "\n", true);
    	
    	FileHelper.writeToFile(CONFIG, "#Securely Generated Salt\n" +
     			"#NOTE: This is generated the first time you run the bot\n" +
    			"#DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU'RE DOING (Min. 24 characters)\n" +
    			"Salt = " + salt, true);
    }
    
    private static void createDefaultConfig() {
    	logHandler.notice("NO CONFIGURATION FOUND - CREATING DEFAULT");
    	FileHelper.writeToFile(CONFIG, "#======================================================\n" +
 		   	   "#===   Configuration File for Aradiabot (Rev. 1A)   ===\n" +
 			   "#======================================================\n", false);
     	
     	FileHelper.writeToFile(CONFIG, "# The Version of your bot\n" +
     			"Version = 1.0.0.0\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#The Nickname for your bot\n" +
     			"Nick = Aradiabot\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Your NickServ Password\n" +
     			"#LEAVE BLANK FOR NO PASSWORD\n" +
     			"Password =\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Your Login Name\n" +
     			"Login = AA\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Server to connect to\n" +
     			"Server = irc.esper.net\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Channel(s) to connect to\n" +
     			"Channel = #channel1,#channel2,#channel3\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#The Email Address to use for the Email Client\n" +
     			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
     			"Email =\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#The Email Password for the Email Client\n" +
     			"#LEAVE BLANK TO NOT USE THE EMAIL CLIENT\n" +
     			"EmailPassword =\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Enable or Disable Debugging\n" +
     			"#Default: false\n" +
     			"Debug = false\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Number of Backup Logs to keep\n" +
     			"#Default: 10\n" +
     			"Backups = 10\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Enable to disable Password Encryption\n" +
     			"#Default: false\n" +
     			"#NOTE: This will encrypt your NickServ and Email Address password (regardless if they're blank or not).\n" +
     			"#It will still encrypt the blank space. DON'T TOUCH IT. It won't activate anything since it'll still\n" +
     			"#decrypt to an empty space like before.\n" +
     			"Encrypt = false\n", true);
     	
     	FileHelper.writeToFile(CONFIG, "#Securely Generated Salt\n" +
     			"#NOTE: This is generated the first time you run the bot\n" +
     			"#DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU'RE DOING\n" +
     			"Salt =",true);
    }
}