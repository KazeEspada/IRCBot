package com.iarekylew00t.ircbot;

import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.google.Google;
import org.jibble.pircbot.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.Random;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.mail.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class IRCBot extends PircBot {

    private static final String VER = "0.9.3.2a";
    private static final String SONG_LIST = "songs.txt";
    private static final String FEEDBACK_FILE = "feedback.txt";
    private static final String ARADIA_QUOTES = "./quotes/aradia-quotes.txt";
    private static final String ARANEA_QUOTES = "./quotes/aranea-quotes.txt";
    private static final String ARQUIUS_QUOTES = "./quotes/arquiusprite-quotes.txt";
    private static final String CALIBORN_QUOTES = "./quotes/caliborn-quotes.txt";
    private static final String CALLIOPE_QUOTES = "./quotes/calliope-quotes.txt";
    private static final String CALSPRITE_QUOTES = "./quotes/calsprite-quotes.txt";
    private static final String CRONUS_QUOTES = "./quotes/cronus-quotes.txt";
    private static final String DAMARA_QUOTES = "./quotes/damara-quotes.txt";
    private static final String DAVE_QUOTES = "./quotes/dave-quotes.txt";
    private static final String DAVESPRITE_QUOTES = "./quotes/davesprite-quotes.txt";
    private static final String DRAGONSPRITE_QUOTES = "./quotes/dragonsprite-quotes.txt";
    private static final String EQUIUS_QUOTES = "./quotes/equius-quotes.txt";
    private static final String ERIDAN_QUOTES = "./quotes/eridan-quotes.txt";
    private static final String ERISOL_QUOTES = "./quotes/erisolsprite-quotes.txt";
    private static final String FEFERI_QUOTES = "./quotes/feferi-quotes.txt";
    private static final String FEFETA_QUOTES = "./quotes/fefetasprite-quotes.txt";
    private static final String GAMZEE_QUOTES = "./quotes/gamzee-quotes.txt";
    private static final String HORUSS_QUOTES = "./quotes/horuss-quotes.txt";
    private static final String JADE_QUOTES = "./quotes/jade-quotes.txt";
    private static final String JADESPRITE_QUOTES = "./quotes/jadesprite-quotes.txt";
    private static final String JAKE_QUOTES = "./quotes/jake-quotes.txt";
    private static final String JASPER_QUOTES = "./quotes/jaspersprite-quotes.txt";
    private static final String JOHN_QUOTES = "./quotes/john-quotes.txt";
    private static final String KANAYA_QUOTES = "./quotes/kanaya-quotes.txt";
    private static final String KANKRI_QUOTES = "./quotes/kankri-quotes.txt";
    private static final String KARKAT_QUOTES = "./quotes/karkat-quotes.txt";
    private static final String KURLOZ_QUOTES = "./quotes/kurloz-quotes.txt";
    private static final String LATULA_QUOTES = "./quotes/latula-quotes.txt";
    private static final String MEENAH_QUOTES = "./quotes/meenah-quotes.txt";
    private static final String MEULIN_QUOTES = "./quotes/meulin-quotes.txt";
    private static final String MITUNA_QUOTES = "./quotes/mituna-quotes.txt";
    private static final String NANNA_QUOTES = "./quotes/nannasprite-quotes.txt";
    private static final String NEPETA_QUOTES = "./quotes/nepeta-quotes.txt";
    private static final String PORRIM_QUOTES = "./quotes/porrim-quotes.txt";
    private static final String RUFIOH_QUOTES = "./quotes/rufioh-quotes.txt";
    private static final String SOLLUX_QUOTES = "./quotes/sollux-quotes.txt";
    private static final String TAVROS_QUOTES = "./quotes/tavros-quotes.txt";
    private static final String TEREZI_QUOTES = "./quotes/terezi-quotes.txt";
    private static final String VRISKA_QUOTES = "./quotes/vriska-quotes.txt";
    private static final String ANDREW_QUOTES = "./quotes/andrew-quotes.txt";
    private static final String SQUAREWAVE_QUOTES = "./quotes/squarewave-quotes.txt";
    private static final String DOC_QUOTES = "./quotes/doc-quotes.txt";
    private static final String ROSE_QUOTES = "./quotes/rose-quotes.txt";
    private static final String ROXY_QUOTES = "./quotes/roxy-quotes.txt";
    private static final String DIRK_QUOTES = "./quotes/dirk-quotes.txt";
    private static final String JANE_QUOTES = "./quotes/jane-quotes.txt";
    private static final String HIC_QUOTES = "./quotes/hic-quotes.txt";
    private static final String TAVRIS_QUOTES = "./quotes/tavrisprite-quotes.txt";
    private static final String REQ_FILE = "songs.txt";
    private static final String HS_LINKS = "links.txt";
    private static final String[] eightBall = {"it is certain", "it is decidedly s0", "yes - definitely", "y0u may rely 0n it", "as i see it, yes", "m0st likely", "0utl00k g00d", "yes", "signs p0int t0 yes", "reply hazy, try again", "ask again later", "better not tell y0u n0w", "cann0t predict n0w", "c0ncentrate and ask again", "d0nt c0unt 0n it", "my reply is n0", "my s0urces say n0", "very d0ubtful"};
    private static final String[] chanList = {"#hs_radio", "#hs_radio2", "#hs_radio3", "#hs_radio4"};
    private static final String[] fastList = {"im g0ing s0 fast","g0in fast", "g0ggg--gg0g0g0g0 fast", "fastfsf than y0u", "t00 fast man", "g0tta g0 fasfters"};
    private String curChan, curTime, voteTitle = "";
    private boolean req = false, openVote = false;
    private int voteYes, voteNo;
    List<String> voterList = new ArrayList<String>();
    private EmailClient email;
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private Date date;
	private LogHandler logger = new LogHandler();
	private Google google = new Google("AIzaSyCBCyKYkO3zcMrBAVsOkyBr5C0GhoGyDXw");
	private MusicHandler player;
	private HomestuckChecker updater = new HomestuckChecker(this, 30);
    
    public IRCBot(String name, String password) {
        setName(name);
        login(password);
        checkFile(SONG_LIST);
        checkFile(FEEDBACK_FILE);
        player = new MusicHandler("curSong.txt", this, email);
    }    
    
    public IRCBot(String name, String password, String emailAcc, String emailPass) {
        setName(name);
        login(password);
        checkFile(SONG_LIST);
        checkFile(FEEDBACK_FILE);
        setupEmail(emailAcc, emailPass);
        player = new MusicHandler("curSong.txt", this, email);
    }
    
    private final void login(String pass) {
    	if (!pass.equals("")){
    		sendMessage("NICKSERV", "IDENTIFY " + pass);
    	}
    }
    
    protected void onConnect() {
    	logger.notice("SUCCESSFULLY CONNECTED TO SERVER");
    }
    
    protected void onDisconnect() {
		logger.warning("DISCONNECTED FROM SERVER");
    	date = new Date();
    	curTime = dateFormat.format(date);
		logger.notice("DISPOSING OF OLD BOT");
		this.dispose();
		try {
			logger.notice("RECREATING BOT AND RECONNECTING");
			IRCBotMain.setupBot();
		} catch (Exception e) {
			logger.error("RECREATION/RECONNECT FAILED");
			try {
				email.sendEmail("kyle10468@gmail.com", "WARNING: Aradiabot Failed to Reconnect", "Aradiabot failed to reconnect to the server @ " + curTime + "\n" + e);
			} catch (MessagingException e1) {
				logger.error(e1);
			}
			logger.error(e);
			return;
		}
		logger.notice("SUCCESSFULLY RECONNECTED");
		try {
			email.sendEmail("kyle10468@gmail.com", "NOTICE: Aradiabot Reconnected Successfully", "Aradiabot successfully reconnected @ " + curTime);
		} catch (MessagingException e) {
			logger.error(e);
		}
    }
    
    protected void onAction(String sender, String login, String hostname, String target, String action) {
		logger.log(sender + "!" + login + ": " + action);
    }
    
    protected void onJoin(String channel, String sender, String login, String hostname) {
    	logger.notice(sender + "!" + login + "@" + hostname + " JOINED " + channel);
    }
    
    protected void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
    	logger.notice(sourceNick + " SET MODE " + mode);
    }
    
    protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
    	logger.notice(oldNick + "!" + login + " SET NICK " + newNick);
    }
    
    protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
    	logger.notice(sourceNick + "!" + sourceLogin + " NOTICE " + target + ": " + notice);
    }
    
    protected void onPart(String channel, String sender, String login, String hostname) {
    	logger.notice(sender + "!" + login + "@" + hostname + " PARTED " + channel);
    }
    
    protected void onPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue) {
    	logger.notice(sourceNick + "!" + sourceLogin + " PINGED " + target);
    }
    
    protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
    	logger.notice(sourceNick + "!" + sourceLogin + "@" + sourceHostname + " QUIT (" + reason + ")");
    }
    
    protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
    	logger.warning(kickerNick + "!" + kickerLogin + "@" + kickerHostname + "(" + channel + ")" + " KICKED " + recipientNick + "(" + reason + ")");
    }
    
    public void log(String line) {
    	if (line.startsWith("PING")) {
    		logger.notice(line);
    	} else if (line.startsWith(">>>")) {
    		logger.notice(line);
    	}
    }
    
    
	public synchronized void onMessage(String channel, String sender, String login, String hostname, String message) {
		logger.log(sender + "!" + login + " (" + channel + "): " + message);
    	date = new Date();
    	curTime = dateFormat.format(date);
    	curChan = channel;
    	
    	//Only check commands if it start's with command symbol ($)
    	if (message.startsWith("$")) {
			//List All Commands
			if (message.equalsIgnoreCase("$commands") || message.equalsIgnoreCase("$help")) {
		        sendMessage(channel, "8ball, announce, bind, boner, commands, dict, expand, faq, feedback, gearup, google, " +
		        		"gofast, gottagofast, heal, irc, kill, latest, lmtyahs, marco, mspa, mspawiki, page, pap, pin, ping, playflute, prevsong, reboot, " +
		        		"radio, req, reqoff, reqon, restart, revive, search, serve, shoosh, shooshpap, shoot, shorten, shout, slap, slay, " +
		        		"song, songlist, stab, submit, talk, tellkyle, time, udict, ver, weather, wiki, youtube");
		  
		    //Reboot Aradiabot (Disconnect, Dispose, Recreate and reconnect)
		    } else if (message.equalsIgnoreCase("$reboot")) {
		    	if (checkOp(sender) || checkVoice(sender)) {
		    		sendMessage(channel, Colors.BOLD + "REBOOTING ARADIABOT");
		    		this.disconnect();
		    	} else {
		    		sendMessage(channel, "im s0rry but y0u d0nt have permiss0n t0 d0 that");
		    	}
		
		    } else if (message.equalsIgnoreCase("$shorten")) {
		    	sendMessage(channel, "please give me a url t0 sh0rten " + sender);
		
		    } else if (message.startsWith("$shorten ")) {
		        String input = message.substring(9);
		        if (input.equals("")) {
		        	sendMessage(channel, "please give me a url t0 sh0rten " + sender);
		        } else {
		        	String shortUrl = "";
					try {
						shortUrl = google.shortenUrl(input);
					} catch (Exception e) {
						logger.error(e);
		            	sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not shrink URL");
		            	return;
					}
		        	sendMessage(channel, "here is y0ur sh0rtened url " + sender + ": " + shortUrl);
		        }
		
		    } else if (message.equalsIgnoreCase("$expand")) {
		    	sendMessage(channel, "please give me a goo.gl url t0 sh0rten " + sender);
		
		    } else if (message.startsWith("$expand ")) {
		        String input = message.substring(8);
		        if (input.equals("")) {
		        	sendMessage(channel, "please give me a goo.gl url t0 expand " + sender);
		        } else if (!input.contains("goo.gl")) {
		        	sendMessage(channel, "im s0rry but i 0nly w0rk with goo.gl urls");
		        } else {
		        	String longUrl = "";
					try {
						longUrl = google.expandUrl(input);
					} catch (Exception e) {
						logger.error(e);
		            	sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not expand URL");
		            	return;
					}
		        	sendMessage(channel, "here is y0ur expanded url " + sender + ": " + longUrl);
		        }
		    	
		    	
		    //TellKyle Command (Email)
		    } else if (message.equalsIgnoreCase("$tellkyle")) {
		    	if (checkOp(sender) || checkVoice(sender)) {
		    		sendMessage(channel, "please add s0mething t0 tell him " + sender);
		    	} else {
		    		sendMessage(channel, "im s0rry but y0u d0nt have permiss0n t0 d0 that");
		    	}
		    } else if (message.startsWith("$tellkyle ")) {
		        String input = message.substring(10);
		    	if (checkOp(sender) || checkVoice(sender)) {
		            if (input.equals("")){
		        		sendMessage(channel, "please add s0mething t0 tell him " + sender);
		            } else {
		            	try {
							email.sendEmail("kyle10468@gmail.com", "Message from " + sender, "Sent @ " + curTime + ": " + input);
							sendMessage(channel, Colors.GREEN + "-- Email sent successfully --");
						} catch (MessagingException e) {
			        		sendMessage(channel, Colors.BOLD + Colors.RED + "ERROR: " + Colors.NORMAL + "Failed to send IAreKyleW00t the message");
			    			logger.error(e);
						}
		            }
		    	} else {
		    		sendMessage(channel, "im s0rry but y0u d0nt have permiss0n t0 d0 that");
		    	}
		        
		    //Current Time    
		    } else if (message.equalsIgnoreCase("$time")) {
		        sendMessage(channel, sender + ": the time is " + curTime);
		        
		    //boner
		    } else if (message.equalsIgnoreCase("$boner")) {
		        sendMessage(channel, sender + ": b0ner");
		    
		    //Let me tell you about Homestuck
		    } else if (message.equalsIgnoreCase("$lmtyahs")) {
		        sendMessage(channel, "let me tell y0u ab0ut h0mestuck: http://goo.gl/XFYbz");
		       
		    //Gear Up Command
		    } else if (message.equalsIgnoreCase("$gearup")) {
		        sendMessage(channel, "y0u are n0w geared up " + sender);
		       
		    //Aradiabot version
		    } else if (message.equalsIgnoreCase("$ver")) {
		        sendMessage(channel, "Aradiabot v" + VER);
		        
		    //Ping Command
		    } else if (message.equalsIgnoreCase("$ping")) {
		        sendMessage(channel, sender + ": p0ng");
		        
		    //Marco/Polo Command 
		    } else if (message.equalsIgnoreCase("$marco")) {
		        sendMessage(channel, sender + ": p0l0");
		        
		    //Songlist Command
		    } else if (message.equalsIgnoreCase("$songlist")) {
		        sendMessage(channel, sender + ": http://goo.gl/0xJtz");
		    
		    //IRC Command
		    } else if (message.equalsIgnoreCase("$irc")) {
		        sendMessage(channel, sender + ": http://goo.gl/dIfQu");
		        
		    //MSPA Page Command
		    } else if (message.equalsIgnoreCase("$mspa")) {
		        sendMessage(channel, sender + ": http://www.mspaintadventures.com/");
		    
		    //Radio Link Command
		    } else if (message.equalsIgnoreCase("$radio")) {
		        sendMessage(channel, sender + ": http://mixlr.com/iarekylew00t/");
		        
		    //FAQ
		    } else if (message.equalsIgnoreCase("$faq")) {
		        sendMessage(channel, sender + ": http://goo.gl/53qWN");
		        
		    //Submit
		    } else if (message.equalsIgnoreCase("$submit")) {
		        sendMessage(channel, sender + ": http://goo.gl/dhvwC");
		
		    //Playflute
		    } else if (message.equalsIgnoreCase("$playflute")) {
		        sendMessage(channel, "have fun " + sender + ": http://goo.gl/LpK89");
		        
		    //Gotta go fast
		    } else if (message.equalsIgnoreCase("$gottagofast")) {
		        sendMessage(channel, "g0tta g0 fastfs: http://goo.gl/P1av7");
		        
		    //Go Fast
		    } else if (message.equalsIgnoreCase("$gofast")) {
		        sendMessage(channel, goFast(fastList));
		    
		    //Search for a page in HS
		    } else if (message.equalsIgnoreCase("$search")) {
		        sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		    } else if (message.startsWith("$search ")) {
		        String input = message.substring(8);
		        try {
					sendMessage(channel, searchPage(input));
				} catch (IOException e) {
					logger.error(e);
				}
		        
		    //8ball
		    } else if (message.equalsIgnoreCase("$8ball")) {
		        sendMessage(channel, "please give me s0mething t0 predict " + sender);
		    } else if (message.startsWith("$8ball ")) {
		        String input = message.substring(7);
		        if (input.equals("")){
		            sendMessage(channel, "please give me s0mething t0 predict " + sender);
		        } else {
		            sendMessage(channel, sender + ": " + getOutcome(eightBall));
		        }
		        
		    //Weather
		    } else if (message.equalsIgnoreCase("$weather") || message.equalsIgnoreCase("$we")) {
		        sendMessage(channel, "please enter a zipc0de " + sender);
		    } else if (message.startsWith("$weather ")) {
		        String input = message.substring(9);
		        if (input.equals("")){
		            sendMessage(channel, "please enter a zipc0de " + sender);
		        } else if (!input.matches("^\\d*$")){
		            sendMessage(channel, "please enter a zipc0de " + sender);
		        } else {
		            sendMessage(channel, sender + ": " + getWeather(input));
		        }
		        
		    //Random Number Generator
		    } else if (message.equalsIgnoreCase("$rand")) {
		        int randNum = (0 + (int)(Math.random() * ((10 - 0)) + 1));
		        sendMessage(channel, sender + ": " + randNum);
		    } else if (message.startsWith("$rand ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
		            int randNum = (0 + (int)(Math.random() * ((10 - 0)) + 1));
		            sendMessage(channel, sender + ": " + randNum);
		        } else {
		        	int maxNum = Integer.parseInt(input);
		            int randNum = (0 + (int)(Math.random() * ((maxNum - 0)) + 1));
		            sendMessage(channel, sender + ": " + randNum);
		        }
		        
		    //Feedback Command
		    } else if (message.equalsIgnoreCase("$feedback") || message.equalsIgnoreCase("$fb")) {
		        sendMessage(channel, "please use: $feedback <resp0nse>");
		    } else if (message.startsWith("$feedback ") || message.startsWith("$fb ")) {
		        String input = message.substring(10);
		        if (input.startsWith("$fb "))
		            input = message.substring(4);
		            
		        if (input.equals("")){
		            sendMessage(channel, "please use: $feedback <resp0nse>");
		        } else {
		            sendMessage(channel, "thank y0u f0r y0ur feedback " + sender + ". kyle will read it s00n" );
		            writeToFile(FEEDBACK_FILE, sender, input);
		        }
		        
		    //Shoosh Command
		    } else if (message.equalsIgnoreCase("$shoosh")) {
		        sendMessage(channel, "wh0 am i supp0se t0 be sh00shing " + sender);
		    } else if (message.startsWith("$shoosh ")) {
		        String input = message.substring(8);
		        if (input.equals("")) {
		            sendMessage(channel, "wh0 am i supp0se t0 be sh00shing " + sender);
		        } else {
		            sendMessage(channel, "sh00shing " + input);
		        }
		
		    //Bind Command
		    } else if (message.equalsIgnoreCase("$bind")) {
		        sendMessage(channel, "wh0 am i supp0se t0 bind " + sender);
		    } else if (message.startsWith("$bind ")) {
		        String input = message.substring(6);
		        if (input.equals("")) {
		            sendMessage(channel, "wh0 am i supp0se t0 bind " + sender);
		        } else {
		            sendMessage(channel, input + " has been b0und");
		        }
		
		    //Pin Command
		    } else if (message.equalsIgnoreCase("$pin")) {
		        sendMessage(channel, "wh0 am i supp0se t0 pin " + sender);
		    } else if (message.startsWith("$pin ")) {
		        String input = message.substring(5);
		        if (input.equals("")) {
		            sendMessage(channel, "wh0 am i supp0se t0 pin " + sender);
		        } else {
		            sendMessage(channel, input + " has been pinned");
		        }
		            
		    //Pap commad
		    } else if (message.equalsIgnoreCase("$pap")) {
		        sendMessage(channel, "wh0 did y0u want me t0 pap " + sender);
		    } else if (message.startsWith("$pap ")) {
		        String input = message.substring(5);
		        if (input.equals("")) {
		            sendMessage(channel, "wh0 did y0u want me t0 pap " + sender);
		        } else {
		            sendMessage(channel, "papping " + input);
		        }
		        
		    //Shooshpap command
		    } else if (message.equalsIgnoreCase("$shooshpap")) {
		        sendMessage(channel, "i need y0u t0 specify wh0 t0 sh00shpap " + sender);
		    } else if (message.startsWith("$shooshpap ")) {
		        String input = message.substring(11);
		        if (input.equals("")) {
		            sendMessage(channel, "i need y0u t0 specify wh0 t0 sh00shpap " + sender);
		        } else {
		            sendMessage(channel, input + " has been sh00shpapped");
		        }
		    
		    //MSPAWiki Search
		    } else if (message.equalsIgnoreCase("$mspawiki")) {
		        sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		    } else if (message.startsWith("$mspawiki ")) {
		        String input = message.substring(10);
		        if (input.equals("")){
		            sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		        } else {
		            String newInput = input.replace(' ','_');
		            sendMessage(channel, "here are y0ur search results " + sender + ": http://mspaintadventures.wikia.com/wiki/index.php?search=" + newInput);
		        }
		        
		    //Wiki Search
		    } else if (message.equalsIgnoreCase("$wiki")) {
		        sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		    } else if (message.startsWith("$wiki ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
		            sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		        } else {
		            String newInput = input.replace(' ','_');
		            sendMessage(channel, "here are y0ur search results " + sender + ": http://en.wikipedia.org/wiki/" + newInput);
		        }
		        
		    //Dictionary Search
		    } else if (message.equalsIgnoreCase("$dict")) {
		        sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		    } else if (message.startsWith("$dict ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
		            sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		        } else {
		            String newInput = input.replace(' ','_');
		            sendMessage(channel, sender + ": here is the definiti0n for " + input + "; http://dictionary.reference.com/browse/" + newInput);
		        }
		        
		    //Urban Dictionary Search
		    } else if (message.equalsIgnoreCase("$udict")) {
		        sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		    } else if (message.startsWith("$udict ")) {
		        String input = message.substring(7);
		        if (input.equals("")){
		            sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		        } else {
		            String newInput = input.replace(' ','+');
		            sendMessage(channel, sender + ": here is the definiti0n for " + input + ": http://www.urbandictionary.com/define.php?term=" + newInput);
		        }
		        
		    //Google Search
		    } else if (message.equalsIgnoreCase("$google") || message.equalsIgnoreCase("$g")) {
		        sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		    } else if (message.startsWith("$google ") || message.startsWith("$g ")) {
		        String input = message.substring(8);
		        if (message.startsWith("$g "))
		        	input = message.substring(3);
		        
		        if (input.equals("")){
		            sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		        } else {
		            String newInput = input.replace(' ','+');
		            sendMessage(channel, "here are y0ur search results " + sender + "; http://www.google.com/search?q=" + newInput);
		        }
		        
		    //Youtube Search
		    } else if (message.equalsIgnoreCase("$youtube") || message.equalsIgnoreCase("$yt")) {
		        sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		    } else if (message.startsWith("$youtube ") || message.startsWith("$yt ")) {
		        String input = message.substring(9);
		        if (message.startsWith("$yt "))
		        	input = message.substring(4);
		        if (input.equals("")){
		            sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
		        } else {
		            String newInput = input.replace(' ','+');
		            sendMessage(channel, "here are y0ur search results " + sender + "; http://www.youtube.com/results?search_query=" + newInput);
		        }
		        
		    //Tumblr
		    } else if (message.equalsIgnoreCase("$tumblr")) {
		        sendMessage(channel, "i need a name " + sender);
		    } else if (message.startsWith("$tumblr ")) {
		        String input = message.substring(8);
		        if (input.equals("")){
		            sendMessage(channel, "i need a name " + sender);
		        } else {
		            sendMessage(channel, sender + ": http://" + input + ".tumblr.com");
		        }
		        
		    //Talk as Aradiabot
		    } else if (message.equalsIgnoreCase("$talk")) {
		        if (channel.equalsIgnoreCase("#hs_admin")) {
		            if (checkOp(sender) == true) {
		                sendMessage(channel, "$talk <channel> <msg>");
		            } else {
		                sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		            }
		        } else {
		            sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		        }
		    } else if (message.startsWith("$talk ")) {
		        if (channel.equalsIgnoreCase("#hs_admin")) {
		            if (checkOp(sender) == true) {
		                String input = message.substring(6);
		                if (input.equals("")){
		                    sendMessage(channel, "$talk <channel> <msg>");
		                } else {
		                    String[] in = input.split(" ");
		                    String argChan, sentence = "";
		                    argChan = in[0];
		                    for (int i = 1; i < in.length; i++) {
		                    	sentence += in[i];
		                    	sentence += " ";
		                    }
		                    sentence = sentence.toLowerCase().replace('o', '0').replace('O', '0');
		                    sendMessage(argChan, sentence);
		                }
		            } else {
		                sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		            }
		        } else {
		            sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		        }
		        
		    //Current Song
		    } else if (message.equalsIgnoreCase("$song")) {
		    	if (!player.getCurSong().startsWith("<Winamp")) {
		    		sendMessage(channel, "the current s0ng is: " + Colors.YELLOW + Colors.BOLD + player.getCurSong());
		    	}
		    	
		    } else if (message.equalsIgnoreCase("$prevsong")) {
		    	sendMessage(channel, "the previ0us s0ng was: " + Colors.YELLOW + Colors.BOLD + player.getPrevSong());
				
			//Latest Homestuck Page
		    } else if (message.equalsIgnoreCase("$latest")) {
		                sendMessage(channel, sender + ": http://www.mspaintadventures.com/?s=6&p=00" + updater.getLatestPage());
		    	
		    //MSPA Update Command
		    } else if (message.equalsIgnoreCase("$update") || message.equalsIgnoreCase("$upd8")) {
		    	if (updater.manualCheck() == true) {
		            sendMessage(channel, Colors.GREEN + Colors.BOLD + "-- THERE IS AN UPDATE --");
		    	} else {
		            sendMessage(channel, "there is n0t an update");
		    	}
		        
		    //Kill Command
		    } else if (message.equalsIgnoreCase("$kill")) {
		        sendMessage(channel, "i need s0me0ne t0 kill " + sender);
		    } else if (message.startsWith("$kill ")) {
		        String input = message.substring(6);
		        if (input.equalsIgnoreCase("aradiabot") || input.equalsIgnoreCase("self")) {
		            sendMessage(channel, "why w0uld i kill myself when im already dead " + sender);
		        } else if (input.equalsIgnoreCase("iarekylew00t")) {
		            sendMessage(channel, "d0 y0u want to end the stati0n " + sender);
		        } else if (input.equals(sender)) {
		            sendMessage(channel, "y0ur n0t all0wed t0 kill y0urself " + sender);
		        } else if (input.equals("")) {
		            sendMessage(channel, "i need s0me0ne t0 kill " + sender);
		        } else {
		          sendMessage(channel, "killing " + input);
		        }
		        
		    //Shoot Command
		    } else if (message.equalsIgnoreCase("$shoot")) {
		        sendMessage(channel, "i need a target " + sender);
		    } else if (message.startsWith("$shoot ")) {
		        String input = message.substring(7);
		        if (input.equalsIgnoreCase("aradiabot") || input.equalsIgnoreCase("self")) {
		            sendMessage(channel, "that w0uld be very stupid 0f me t0 d0 " + sender);
		        } else if (input.equalsIgnoreCase("iarekylew00t")) {
		            sendMessage(channel, "IAreKyleW00t will n0t all0w me t0 d0 that " + sender);
		        } else if (input.equals(sender)) {
		            sendMessage(channel, "im n0t sure why y0u w0uld want t0 sh00t y0urself " + sender);
		        } else if (input.equals("")) {
		            sendMessage(channel, "i need a target " + sender);
		        } else {
		            sendMessage(channel, "sh00ting " + input);
		        }
		        
		    //Revive Command
		    } else if (message.equalsIgnoreCase("$revive")) {
		        sendMessage(channel, "wh0 am i supp0sed t0 revive " + sender);
		    } else if(message.startsWith("$revive ")) {
		        String input = message.substring(8);
		        if (input.equals("")) {
		            sendMessage(channel, "wh0 am i sup0sed t0 revive " + sender);
		        } else if (input.equals(sender)) {
		            sendMessage(channel, "y0u cant just revive y0urself silly");
		        } else {
		            sendMessage(channel, "reviving " + input);
		        }
		        
		    //Heal Command
		    } else if (message.equalsIgnoreCase("$heal")) {
		        sendMessage(channel, "wh0 am i supp0sed t0 heal " + sender);
		    } else if(message.startsWith("$heal ")) {
		        String input = message.substring(6);
		        if (input.equals("")) {
		            sendMessage(channel, "wh0 am i sup0sed t0 heal " + sender);
		        } else if (input.equals(sender)) {
		            sendMessage(channel, "y0u have been healed " + sender);
		        } else {
		            sendMessage(channel, input + " has been healed ");
		        }
		        
		    //Slay Command
		    } else if (message.equalsIgnoreCase("$slay")) {
		        sendMessage(channel, "i need a name " + sender);
		    } else if (message.startsWith("$slay ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
		            sendMessage(channel, "i need s0me0ne t0 slay " + sender);
		        } else if (input.equalsIgnoreCase("aradiabot") || input.equalsIgnoreCase("self")) {
		            sendMessage(channel, "why w0uld i kill myself when im already dead " + sender);
		        } else if (input.equalsIgnoreCase("iarekylew00t")) {
		            sendMessage(channel, "im n0t all0wed t0 d0 that " + sender);
		        } else {
		            sendMessage(channel, input + " has been slain");
		        }
		        
		    //Stab Command
		    } else if (message.equalsIgnoreCase("$stab")) {
		        sendMessage(channel, "i d0nt kn0w wh0 y0u want me t0 stab " + sender);
		    } else if (message.startsWith("$stab ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
		            sendMessage(channel, "i need a name " + sender);
		        } else if (input.equals(sender)) {
		            sendMessage(channel, "y0u want t0 stab y0urself... 0k");
		        } else {
		            sendMessage(channel, input + " has been stabbed");
		        }
		        
		    //Slap Command
		    } else if (message.equalsIgnoreCase("$slap")) {
		        sendMessage(channel, "wh0 sh0uld i slap " + sender);
		    } else if (message.startsWith("$slap ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
		            sendMessage(channel, "wh0 sh0uld i slap " + sender);
		        } else if (input.equals(sender)) {
		            sendMessage(channel, "y0u just slapped y0urself " + sender + ". I h0pe y0ure pr0ud");
		        } else {
		            sendMessage(channel, "slapping " + input);
		        }
		        
		    //Serve Command
		    } else if (message.equalsIgnoreCase("$serve")) {
		        sendMessage(channel, "i cann0t just serve n0thing " + sender);
		    } else if (message.startsWith("$serve ")) {
		        String input = message.substring(7);
		        if (input.equals("")){
		            sendMessage(channel, "i cann0t serve n0thing");
		        } else {
		            sendMessage(channel, "serving " + input + " t0 everyone");
		        }
		    
		
		    //Vote Command
		    } else if (message.equalsIgnoreCase("$vote")) {
		    	if (checkOp(sender) == true || checkVoice(sender) == true) {
		        sendMessage(channel, "please specify what pe0ple are v0ting f0r " + sender);
		    	} else  if (openVote == false){
		            sendMessage(channel, "y0u d0 n0t have permissi0n t0 d0 that " + sender);
		    	} else {
		            sendMessage(channel, "please v0te yes 0r n0 " + sender);
		    	}
		    } else if (message.startsWith("$vote ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
		        	if (openVote == false) {
		        		if (checkOp(sender) == true || checkVoice(sender) == true) {
		        			sendMessage(channel, "please specify what pe0ple are v0ting f0r " + sender);
		        		} else {
		        			sendMessage(channel, "y0ure n0t all0wed t0 d0 that");
		        		}
		        	} else {
		                sendMessage(channel, "please v0te yes 0r n0 " + sender);
		        	}
		        } else {
		            String[] in = input.split(" ");
		            if (in[0].equalsIgnoreCase("open")) {
		            	if (checkOp(sender) == true || checkVoice(sender) == true) {
		                	if (openVote == true) {
		                        sendMessage(channel, "there is already a p0ll 0pen " + sender);
		                	} else {
			                	openVote = true;
			                    for (int i = 1; i < in.length; i++) {
			                    	voteTitle += in[i];
			                    	voteTitle += " ";
			                    }
		                        sendMessage(channel, sender + " has 0pened a p0ll with the t0pic: " + voteTitle);
		                	}
		            	} else {
		        			sendMessage(channel, "y0ure n0t all0wed t0 d0 that");
		            	}
		            } else if (in[0].equalsIgnoreCase("close")) {
		            	if (checkOp(sender) == true || checkVoice(sender) == true) {
		                	if (openVote == false) {
		                        sendMessage(channel, "there isnt a p0ll 0pen " + sender);
		                	} else {
		                		openVote = false;
			                    sendMessage(channel, sender + " has cl0sed the p0ll: " + voteTitle);
			                    sendMessage(channel, "the results were: " + voteYes + "/" + voteNo);
		                		voteYes = 0;
		                		voteNo = 0;
		                		voterList.clear();
		                		voteTitle = "";
		                	}
		            	} else {
		        			sendMessage(channel, "y0ure n0t all0wed t0 d0 that " + sender);
		            	}
		            } else if (in[0].equalsIgnoreCase("yes") || in[0].equalsIgnoreCase("y")) {
		            	if (voterList.contains(sender) == false) {
		                	if (openVote == true) {
		                		voteYes++;
		                		voterList.add(sender);
		                        sendMessage(channel, voteYes + "/" + voteNo);
		                	} else {
		                        sendMessage(channel, "there isnt a p0ll 0pen " + sender);
		                	}
		            	} else {
		                    sendMessage(channel, "y0u have already v0ted " + sender);
		            	}
		            } else if (in[0].equalsIgnoreCase("no") || in[0].equalsIgnoreCase("n")) {
		            	if (voterList.contains(sender) == false) {
		                	if (openVote == true) {
		                		voteNo++;
		                		voterList.add(sender);
		                        sendMessage(channel, voteYes + "/" + voteNo);
		                	} else {
		                        sendMessage(channel, "there isnt a p0ll 0pen " + sender);
		                	}
		            	} else {
		                    sendMessage(channel, "y0u have already v0ted " + sender);
		            	}
		            } else if (openVote == true && (!in[0].equalsIgnoreCase("yes") || !in[0].equalsIgnoreCase("y") || !in[0].equalsIgnoreCase("no") || !in[0].equalsIgnoreCase("n"))){
		            	if (voterList.contains(sender) == false) {
		                		sendMessage(channel, "please v0te either yes 0r n0 " + sender);	
		            	} else {
		                    sendMessage(channel, "y0u have already v0ted " + sender);
		            	}
		            }
		        }
		        
		    //Quote Command
		    } else if (message.equalsIgnoreCase("$quote")) {
					sendMessage(channel, "please specify a character " + sender);
		    } else if (message.startsWith("$quote ")) {
		        String input = message.substring(7);
		        if (input.equals("")){
					sendMessage(channel, "please specify a character " + sender);
		        } else if (!input.contains(" ")){
					try {
						sendMessage(channel, getRandQuote(input));
					} catch (IOException e) {
						logger.error(e);
					}
		        } else {
		        	String[] in = input.split(" ");
		        	int num = Integer.parseInt(in[1]);
		            try {
						sendMessage(channel, getQuote(in[0], num));
					} catch (IOException e) {
						logger.error(e);
					}
		        }
		        
		    //Page Command
		    } else if (message.equalsIgnoreCase("$page")) {
					sendMessage(channel, sender + ": http://www.mspaintadventures.com/?s=6&p=00" + updater.getPage());
		    } else if (message.startsWith("$page ")) {
		        String input = message.substring(6);
		        if (input.equals("")){
					sendMessage(channel, sender + ": http://www.mspaintadventures.com/?s=6&p=00" + updater.getPage());
		        } else {
		        	int pageNum = Integer.parseInt(input);
		        	if (pageNum == 4299 || pageNum == 4938 || pageNum == 4988) {
						sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "That page does not exist");
		        	} else {
						sendMessage(channel, sender + ": http://www.mspaintadventures.com/?s=6&p=00" + updater.getPage(pageNum));
		        	}
		        }
		   
		    //Shout Command
		    } else if (message.equalsIgnoreCase("$shout")) {
		    	if (checkOp(sender) == false || checkVoice(sender) == false) {
		            sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		        } else {
		            sendMessage(channel, "please use: $shout <message>");
		        }
		    } else if (message.startsWith("$shout ")) {
		    	if (checkOp(sender) || checkVoice(sender)) {
		        	String input = message.substring(7);
		        	sendMessage(channel, Colors.BOLD + Colors.RED + input);
		        } else {
		            sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		        }
		        
		    //Announce Command
		    } else if (message.equalsIgnoreCase("$announce")) {
		    	if (checkOp(sender) == false || checkVoice(sender) == false) {
		            sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		    	} else if (channel.equalsIgnoreCase("#ircstuck") && checkOp(sender)) {
		                sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		        } else {
		            sendMessage(channel, "please use: $announce <message>");
		        }
		    } else if (message.startsWith("$announce ")) {
		    	if (checkOp(sender)) {
		        	String input = message.substring(10);
		            for (int i = 0; i < chanList.length; i++) {
		            	sendMessage(chanList[i], Colors.BOLD + Colors.RED + "ANNOUNCEMENT: " + Colors.YELLOW + input);
		            }
		    	} else if (channel.equalsIgnoreCase("#ircstuck") && checkOp(sender)) {
		            sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		        } else {
		            sendMessage(channel, "y0u d0nt have permissi0n t0 d0 that");
		        }
		        	
		    //Request ON|OFF
		    } else if (message.equalsIgnoreCase("$reqon")) {
		        if (channel.equalsIgnoreCase("#ircstuck")) {
		            sendMessage(channel, "y0u may n0t use that c0mmand in this channel");
		        } else {
		            if (checkOp(sender) == true) {
		                req = true;
		                for (int i = 0; i < chanList.length; i++) {
		                sendMessage(chanList[i], Colors.BOLD + Colors.GREEN + "requests have been turn 0n by " + sender);
		                }
		            } else {
		                sendMessage(channel, "im n0t all0wed t0 let y0u d0 that " + sender);
		            }
		        }
		    } else if (message.equalsIgnoreCase("$reqoff")) {
		        if (channel.equalsIgnoreCase("#ircstuck")) {
		            sendMessage(channel, "y0u may n0t use that c0mmand in this channel");
		        } else {
		            if (checkOp(sender) == true) {
		                req = false;
		                for (int i = 0; i < chanList.length; i++) {
		                sendMessage(chanList[i], Colors.BOLD + Colors.RED + "requests have been turn 0ff by " + sender);
		                }
		            } else {
		                sendMessage(channel, "im n0t all0wed t0 let y0u d0 that " + sender);
		            }
		        }
		  
		    //Request song
		    } else if (message.equalsIgnoreCase("$req")) {
		        if (req == true)
		            sendMessage(channel, "requests are currently 0pen; please use: $req <s0ngname>");
		        else
		            sendMessage(channel, "requests are currently cl0sed");
		    } else if (message.startsWith("$req ")) {
		        String input = message.substring(5);
		        if (req == true) {
		        	try {
						if (countReq(sender) < 3) {
						    if (input.equals("")){
						        sendMessage(channel, "please specify a s0ng " + sender);
						    } else {
						    	if (checkReq(input) == false) {
							        sendMessage(channel, "y0ur s0ng request has been added t0 the list " + sender);
							        writeToFile(SONG_LIST, sender, input);
						    	} else {
							        sendMessage(channel, "that s0ng has already been requested " + sender);
						    	}
						    }
						} else {
					        sendMessage(channel, "y0u have already made 3 requests " + sender);
						}
					} catch (IOException e) {
						logger.error(e);
					}
		        } else {
		            sendMessage(channel, "requests are currently cl0sed " + sender);
		        }
		        
		    //Restart Winamp Command
		    } else if (message.equalsIgnoreCase("$restart")) {
		    	if (checkOp(sender) || checkVoice(sender)) {
		    		try {
						player.restartWinamp(channel, sender);
					} catch (Exception e) {
						try {
							sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Failed to restart Winamp - Notifying IAreKyleW00t");
							email.sendEmail("kyle10468@gmail.com", "WARNING: Winamp Failed to Restart", "Winamp FAILED to resatrt @ " + curTime);
						} catch (MessagingException e1) {
							sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not send Email - please notify IAreKyleW00t: http://iarekylew00t.tumblr.com/ask");
							logger.error("FAILED TO SEND EMAIL");
							logger.error(e1);
						}
						logger.error("FAILED TO RESTART WINAMP");
						logger.error(e);
					}
		    	} else {
		            sendMessage(channel, "im s0rry, y0u d0nt have permissi0n t0 d0 that - please c0ntact a m0d 0r admin");
		    	}
		    }
    	}
    }
    
    private static int countLines(String filename) throws IOException {
	    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
		int cnt = 0;
		@SuppressWarnings("unused")
		String lineRead = "";
		while ((lineRead = reader.readLine()) != null) {}
	
		cnt = reader.getLineNumber(); 
		reader.close();
		return cnt;
	}
    
	private int countReq(String snd) throws IOException {
		String line;
        int count = 0;
		BufferedReader br = new BufferedReader(new FileReader(REQ_FILE));
        while ((line = br.readLine()) != null) {
			if (line.startsWith(snd + ":")) {
				count++;
			}
		}
		br.close();
		
		return count;
    }
	
	@SuppressWarnings("resource")
	private String getQuote(String c, int num) throws IOException {
		String quote = "";
		if (c.equalsIgnoreCase("jade")) {
			int lines = countLines(JADE_QUOTES);
				if (num > lines || num <= 0) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JADE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("dave")) {
			int lines = countLines(DAVE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DAVE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("dirk")) {
			int lines = countLines(DIRK_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DIRK_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("rose")) {
			int lines = countLines(ROSE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ROSE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("roxy")) {
			int lines = countLines(ROXY_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ROXY_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("jane")) {
			int lines = countLines(JANE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JANE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("aradia")) {
			int lines = countLines(ARADIA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ARADIA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("aranea")) {
			int lines = countLines(ARANEA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ARANEA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("arquius") || c.equalsIgnoreCase("arquiusprite")) {
			int lines = countLines(ARQUIUS_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ARQUIUS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("caliborn")) {
			int lines = countLines(CALIBORN_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CALIBORN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("calliope")) {
			int lines = countLines(CALLIOPE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CALLIOPE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("calsprite") || c.equalsIgnoreCase("cal")) {
			int lines = countLines(CALSPRITE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CALSPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("cronus")) {
			int lines = countLines(CRONUS_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CRONUS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("damara")) {
			int lines = countLines(DAMARA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DAMARA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("davesprite")) {
			int lines = countLines(DAVESPRITE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DAVESPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("dragon") || c.equalsIgnoreCase("dragonsprite")) {
			int lines = countLines(DRAGONSPRITE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DRAGONSPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("equius")) {
			int lines = countLines(EQUIUS_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(EQUIUS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("eridan")) {
			int lines = countLines(ERIDAN_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ERIDAN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("erisol") || c.equalsIgnoreCase("erisolsprite")) {
			int lines = countLines(ERISOL_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ERISOL_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("feferi")) {
			int lines = countLines(FEFERI_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(FEFERI_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("fefeta") || c.equalsIgnoreCase("fefetasprite")) {
			int lines = countLines(FEFETA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(FEFETA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("gamzee")) {
			int lines = countLines(GAMZEE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(GAMZEE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("horuss")) {
			int lines = countLines(HORUSS_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(HORUSS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("jake")) {
			int lines = countLines(JAKE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JAKE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("jasper") || c.equalsIgnoreCase("jaspersprite") || c.equalsIgnoreCase("jaspers")) {
			int lines = countLines(JASPER_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JASPER_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("john")) {
			int lines = countLines(JOHN_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JOHN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("kanaya")) {
			int lines = countLines(KANAYA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KANAYA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("kankri")) {
			int lines = countLines(KANKRI_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KANKRI_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("karkat")) {
			int lines = countLines(KARKAT_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KARKAT_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("kurloz")) {
			int lines = countLines(KURLOZ_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KURLOZ_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("latula")) {
			int lines = countLines(LATULA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(LATULA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("meenah")) {
			int lines = countLines(MEENAH_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(MEENAH_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("meulin")) {
			int lines = countLines(MEULIN_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(MEULIN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("mituna")) {
			int lines = countLines(MITUNA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(MITUNA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("nanna") || c.equalsIgnoreCase("nannasprite")) {
			int lines = countLines(NANNA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(NANNA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("nepeta")) {
			int lines = countLines(NEPETA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(NEPETA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("porrim")) {
			int lines = countLines(PORRIM_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(PORRIM_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("rufioh")) {
			int lines = countLines(RUFIOH_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(RUFIOH_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("sollux")) {
			int lines = countLines(SOLLUX_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(SOLLUX_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("tavros")) {
			int lines = countLines(TAVROS_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(TAVROS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("terezi")) {
			int lines = countLines(TEREZI_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(TEREZI_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("vriska")) {
			int lines = countLines(VRISKA_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(VRISKA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("doc") || c.equalsIgnoreCase("doc scratch")) {
			int lines = countLines(DOC_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DOC_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("andrew") || c.equalsIgnoreCase("andrew hussie") || c.equalsIgnoreCase("hussie")) {
			int lines = countLines(ANDREW_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ANDREW_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("jadesprite")) {
			int lines = countLines(JADESPRITE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JADESPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("squarewave")) {
			int lines = countLines(SQUAREWAVE_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(SQUAREWAVE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("hic") || c.equalsIgnoreCase(")(ic")) {
			int lines = countLines(HIC_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(HIC_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else if (c.equalsIgnoreCase("tavris") || c.equalsIgnoreCase("tavrisprite")) {
			int lines = countLines(TAVRIS_QUOTES);
				if (num > lines || num < 1) {
					return "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(TAVRIS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} else {
                return "there are n0 qu0tes f0r that character right n0w. please d0uble check y0ur spelling as well";
			}
		return Colors.TEAL + Colors.BOLD + "Quote #" + (num) + " - " + Colors.NORMAL + quote;
	}
	
	@SuppressWarnings("resource")
	private String getRandQuote(String c) throws IOException {
		String quote = "";
		int quoteNum = 0;
		if (c.equalsIgnoreCase("jade")) {
			int lines = countLines(JADE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(JADE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("dave")) {
			int lines = countLines(DAVE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(DAVE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("rose")) {
			int lines = countLines(ROSE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ROSE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("dirk")) {
			int lines = countLines(DIRK_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(DIRK_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("roxy")) {
			int lines = countLines(ROXY_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ROXY_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote =  br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("jane")) {
			int lines = countLines(JANE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(JANE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("aradia")) {
			int lines = countLines(ARADIA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ARADIA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("aranea")) {
			int lines = countLines(ARANEA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ARANEA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("arquius") || c.equalsIgnoreCase("arquiusprite")) {
			int lines = countLines(ARQUIUS_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ARQUIUS_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("caliborn")) {
			int lines = countLines(CALIBORN_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(CALIBORN_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("calliope")) {
			int lines = countLines(CALLIOPE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(CALLIOPE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("calsprite") || c.equalsIgnoreCase("cal")) {
			int lines = countLines(CALSPRITE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(CALSPRITE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("cronus")) {
			int lines = countLines(CRONUS_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(CRONUS_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("damara")) {
			int lines = countLines(DAMARA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(DAMARA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("davesprite")) {
			int lines = countLines(DAVESPRITE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(DAVESPRITE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("dragon") || c.equalsIgnoreCase("dragonsprite")) {
			int lines = countLines(DRAGONSPRITE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(DRAGONSPRITE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("equius")) {
			int lines = countLines(EQUIUS_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(EQUIUS_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("eridan")) {
			int lines = countLines(ERIDAN_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ERIDAN_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("erisol") || c.equalsIgnoreCase("erisolsprite")) {
			int lines = countLines(ERISOL_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ERISOL_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("feferi")) {
			int lines = countLines(FEFERI_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(FEFERI_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("fefeta") || c.equalsIgnoreCase("fefetasprite")) {
			int lines = countLines(FEFETA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(FEFETA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("gamzee")) {
			int lines = countLines(GAMZEE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(GAMZEE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("horuss")) {
			int lines = countLines(HORUSS_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(HORUSS_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("jake")) {
			int lines = countLines(JAKE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(JAKE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("jasper") || c.equalsIgnoreCase("jaspersprite") || c.equalsIgnoreCase("jaspers")) {
			int lines = countLines(JASPER_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(JASPER_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("john")) {
			int lines = countLines(JOHN_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(JOHN_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("kanaya")) {
			int lines = countLines(KANAYA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(KANAYA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("kankri")) {
			int lines = countLines(KANKRI_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(KANKRI_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("karkat")) {
			int lines = countLines(KARKAT_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(KARKAT_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("kurloz")) {
			int lines = countLines(KURLOZ_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(KURLOZ_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("latula")) {
			int lines = countLines(LATULA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(LATULA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("meenah")) {
			int lines = countLines(MEENAH_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(MEENAH_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("meulin")) {
			int lines = countLines(MEULIN_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(MEULIN_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("mituna")) {
			int lines = countLines(MITUNA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(MITUNA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("nanna") || c.equalsIgnoreCase("nannasprite")) {
			int lines = countLines(NANNA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(NANNA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("nepeta")) {
			int lines = countLines(NEPETA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(NEPETA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("porrim")) {
			int lines = countLines(PORRIM_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(PORRIM_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("rufioh")) {
			int lines = countLines(RUFIOH_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(RUFIOH_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("sollux")) {
			int lines = countLines(SOLLUX_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(SOLLUX_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("tavros")) {
			int lines = countLines(TAVROS_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(TAVROS_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("terezi")) {
			int lines = countLines(TEREZI_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(TEREZI_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("vriska")) {
			int lines = countLines(VRISKA_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(VRISKA_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("doc") || c.equalsIgnoreCase("doc scratch")) {
			int lines = countLines(DOC_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(DOC_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("andrew") || c.equalsIgnoreCase("andrew hussie") || c.equalsIgnoreCase("hussie")) {
			int lines = countLines(ANDREW_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(ANDREW_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("jadesprite")) {
			int lines = countLines(JADESPRITE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(JADESPRITE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("squarewave")) {
			int lines = countLines(SQUAREWAVE_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(SQUAREWAVE_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("hic") || c.equalsIgnoreCase(")(ic")) {
			int lines = countLines(HIC_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(HIC_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (c.equalsIgnoreCase("tavris") || c.equalsIgnoreCase("tavrisprite")) {
			int lines = countLines(TAVRIS_QUOTES);
			int randNum = new Random().nextInt(lines);
			quoteNum = randNum;
			try {
				FileInputStream fs= new FileInputStream(TAVRIS_QUOTES);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				for(int i = 0; i < randNum; ++i)
				  br.readLine();
				quote = br.readLine();
			} catch (Exception e) {
				logger.error(e);
			}
		} else {
			return "there are n0 qu0tes f0r that character right n0w. please d0uble check y0ur spelling as well";
		}
			
		return Colors.TEAL + Colors.BOLD + "Quote #" + (quoteNum+1) + " - " + Colors.NORMAL + quote;
	}
    
    private static String getOutcome(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }    
    
    private static String goFast(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
    
    private String getWeather(String zip) {
        String loc = "";
        String tmp = "";
        String flik = "";
        String cond = "";
        try {
            URL weatherXml = new URL("http://xml.weather.com/weather/local/" + zip + "?cc=&unit=f");
            InputStream xml = weatherXml.openStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("cc");
                Node nNode = nList.item(0);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    loc = eElement.getElementsByTagName("obst").item(0).getTextContent();
                    tmp = eElement.getElementsByTagName("tmp").item(0).getTextContent();
                    flik = eElement.getElementsByTagName("flik").item(0).getTextContent();
                    cond = eElement.getElementsByTagName("t").item(0).getTextContent();
                }
        } catch (Exception e) {
        	logger.error("Could not parse weather information from XML");
            logger.error(e);
        }
        
        return Colors.NORMAL + Colors.BOLD + loc + " (" + zip + ")" + Colors.NORMAL + " - it is " + Colors.BOLD + tmp + "F " 
        + Colors.NORMAL + "0utside " + Colors.NORMAL + "(feels like " + Colors.BOLD + flik + "F" + Colors.NORMAL + ") and it is " 
        + Colors.BOLD + cond + Colors.NORMAL + ".";
    }

	@SuppressWarnings("resource")
    private boolean checkReq(String song) throws IOException {
    	String line;
		BufferedReader br = new BufferedReader(new FileReader(SONG_LIST));
		while ((line = br.readLine()) != null) {
			if (line.toLowerCase().contains(song.toLowerCase())) {
				return true;
			}
		}
    	return false;
    }
    
    @SuppressWarnings("unused")
	private String searchPage(String search) throws IOException{
    	int curSearchPage = 0;
    	String line, searchLine = "";
    	String[] splitLine;
		BufferedReader br = new BufferedReader(new FileReader(HS_LINKS));
		while ((line = br.readLine()) != null) {
			curSearchPage++;
			if (line.toLowerCase().contains(search.toLowerCase())) {
				searchLine = line;
				break;
			}
		}
		br.close();
		if (searchLine.equalsIgnoreCase("")){
			return "i c0uld n0t find any page with \"" + search + "\" in it";
		} else {
			splitLine = searchLine.split("\"");
	    	return splitLine[1] + ": " + splitLine[0];
		}
    }

	private void writeToFile(String file, String sender, String msg) {
        try {
            Writer output = new BufferedWriter(new FileWriter(file, true));
            output.append(sender + ": " + msg + "\n");
            output.close();
        }
        catch (Exception e) {
        	logger.error("Error writing to file\n" + e);
        }
    }
	
    private void checkFile(String fileLoc) {
        File file = new File(fileLoc);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private boolean checkOp(String sender) {
    	User users[] = getUsers(curChan);
    	User u = null;
    	for(User user:users){
    	   if(sender.equals(user.getNick())){
    	      u = user;
    	      break;
    	   }
    	}
    	if(u != null){
    	   if(u.isOp()){
    		   return true;
    	   } else {
    		   return false;
    	   }
    	}
    	return false;
    }
    
    public boolean checkVoice(String sender) {
    	User users[] = getUsers(curChan);
    	User u = null;
    	for(User user:users){
    	   if(sender.equals(user.getNick())){
    	      u = user;
    	      break;
    	   }
    	}
    	if(u != null){
    	   if(u.hasVoice()){
    		   return true;
    	   } else {
    		   return false;
    	   }
    	}
    	return false;
    }
    
    private void setupEmail(String emailAcc, String password) {
        if (!emailAcc.equals("") || !password.equals("")) {
		    try {
				email = new EmailClient(emailAcc, password, "smtp.gmail.com", false);
			} catch (Exception e) {
				logger.error("ERROR SETTING UP GMAIL CLIENT");
				logger.error(e);
			}
        }
    }
}