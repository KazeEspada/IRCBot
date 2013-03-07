package com.iarekylew00t.ircbot;

import org.jibble.pircbot.*;
import java.util.*;
import java.util.regex.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.*;
import java.util.Random;
import java.net.URL;

public class IRCBot extends PircBot implements Runnable {

    private static final String VER = "0.9.1.9-beta2";
    private static final String REMINDER_FILE = "reminders.dat";
    private static final String SONG_LIST = "songs.txt";
    private static final String FEEDBACK_FILE = "feedback.txt";
    private static final String CUR_SONG = "curSong.txt";
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
    private static final String[] eightBall = {"it is certain", "it is decidedly s0", "yes - definitely", "y0u may rely 0n it", "as i see it, yes", "m0st likely", "0utl00k g00d", "yes", "signs p0int t0 yes", "reply hazy, try again", "ask again later", "better not tell y0u n0w", "cann0t precit n0w", "c0ncentrate and ask again", "d0nt c0unt 0n it", "my reply is n0", "my s0urces say n0", "very d0ubtful"};
    private String curChan;
    private boolean req = false;
	private static int latestPage;
    private static boolean isUpdate = false;
    
    public IRCBot(String name, String password) {
        loadReminders();
        setName(name);
        login(password);
        setAutoNickChange(true);
        dispatchThread = new Thread(this);
        dispatchThread.start();
        //Check for page update
        checkUpdate();
        //Check for SONG_LIST file
        checkFile(SONG_LIST);
        //Check for FEEDBACK_FILE
        checkFile(FEEDBACK_FILE);
    }
    
    public void login(String pass) {
    	if (pass == ""){
    		//Do Nothing
    	} else if (pass.length() > 0) {
    		sendMessage("NICKSERV", "IDENTIFY " + pass);
    	}
    }
    
    @SuppressWarnings("unchecked")
	public synchronized void onMessage(String channel, String sender, String login, String hostname, String message) {
    	//Keep track of the senders current channel
    	curChan = channel;
        Pattern messagePattern = Pattern.compile("^\\s*(?i:(" + getNick() + ")?\\s*[\\:,]?\\s*remind\\s+me\\s+in\\s+(((\\d+\\.?\\d*|\\.\\d+)\\s+(weeks?|days?|hours?|hrs?|minutes?|mins?|seconds?|secs?)[\\s,]*(and)?\\s+)+)(.*)\\s*)$");
        Matcher m = messagePattern.matcher(message);
        
        //Check if message Matches Reminder Pattern
        if (m.matches()) {
        String reminderMessage = m.group(7);
            String periods = m.group(2);
            long set = System.currentTimeMillis();
            long due = set;
            try {
                double weeks = getPeriod(periods, "weeks|week");
                double days = getPeriod(periods, "days|day");
                double hours = getPeriod(periods, "hours|hrs|hour|hr");
                double minutes = getPeriod(periods, "minutes|mins|minute|min");
                double seconds = getPeriod(periods, "seconds|secs|second|sec");
                due += (weeks * 604800 + days * 86400 + hours * 3600 + minutes * 60 + seconds) * 1000;
            }
            catch (NumberFormatException e) {
                sendMessage(channel, "i cant deal with numbers like that " + sender);
                return;
            }
            if (due == set) {
                sendMessage(channel, "Example of correct usage: \"Remind me in 1 hour, 10 minutes to check the oven.\"  I understand all combinations of weeks, days, hours, minutes and seconds.");
                return;
            }
            Reminder reminder = new Reminder(channel, sender, reminderMessage, set, due);
            sendMessage(channel, "0kay " + sender + ", ill remind y0u ab0ut that 0n " + new Date(reminder.getDueTime()));
            reminders.add(reminder);
            dispatchThread.interrupt();
            
        //List Commands
        } else if (message.equalsIgnoreCase("$commands") || message.equalsIgnoreCase("$help")) {
            sendMessage(channel, "8ball, boner, commands, dict, faq, feedback, gearup, google, heal, kill, lmtyahs, marco, mspa, mspawiki, pap, ping, radio, req, reqoff, reqon, revive, serve, shoosh, shooshpap, shoot, slap, slay, song, songlist, stab, submit, time, udict, ver, weather, wiki, youtube");
      
        //Current Time    
        } else if (message.equalsIgnoreCase("$time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": the time is " + time);
            
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
        
        //MSPA Page Command
        } else if (message.equalsIgnoreCase("$mspa")) {
            sendMessage(channel, sender + ": http://www.mspaintadventures.com/");
        
        //Radio Link Command
        } else if (message.equalsIgnoreCase("$radio")) {
            sendMessage(channel, sender + ": http://mixlr.com/iarekylew00t/");
            
        //FAQ
        } else if (message.equalsIgnoreCase("$faq")) {
            sendMessage(channel, sender + ": http://goo.gl/53qWN/");
            
        //Submit
        } else if (message.equalsIgnoreCase("$submit")) {
            sendMessage(channel, sender + ": http://goo.gl/dhvwC");
            
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
            sendMessage(channel, "please give me s0mething t0 predict " + sender);
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
        } else if (message.equalsIgnoreCase("$google") || message.equalsIgnoreCase("$g") || message.equalsIgnoreCase("$search")) {
            sendMessage(channel, "please give me s0mething t0 search f0r " + sender);
        } else if (message.startsWith("$google ") || message.startsWith("$g ") || message.startsWith("$search ")) {
            String input = message.substring(8);
            if (message.startsWith("$g "))
            	input = message.substring(3);
            else if (message.startsWith("$search "))
            	input = message.substring(8);
            
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
                sendMessage(channel, sender + ": " + input + ".tumblr.com");
            }
            
        //Current Song
        } else if (message.equalsIgnoreCase("$song")) {
            sendMessage(channel, "the current s0ng is: " + Colors.YELLOW + Colors.BOLD + getCurSong());
			
		//Latest Homestuck Page
        } else if (message.equalsIgnoreCase("$latest")) {
                    sendMessage(channel, sender + ": " + getLatestPage());
        	
        //MSPA Update Command
        } else if (message.equalsIgnoreCase("$update") || message.equalsIgnoreCase("$upd8")) {
        	checkUpdate();
        	if (isUpdate == true)
                sendMessage(channel, Colors.GREEN + "there is an update");
            else
                sendMessage(channel, "there is n0 update");
            
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
					e.printStackTrace();
				}
            } else {
            	String[] in = input.split(" ");
            	int num = Integer.parseInt(in[1]);
                try {
					sendMessage(channel, getQuote(in[0], num));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            
        //Request ON|OFF
        } else if (message.equalsIgnoreCase("$reqon")) {
            if (channel.equalsIgnoreCase("#ircstuck")) {
                sendMessage(channel, "y0u may n0t use that c0mmand in this channel");
            } else {
                if (checkOp(sender) == true) {
                    req = true;
                    sendMessage("#hs_admin", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0n by " + sender);
                    sendMessage("#hs_radio", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0n by " + sender);
                    sendMessage("#hs_rp", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0n by " + sender);
                    sendMessage("#hs_nsfw", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0n by " + sender);
                    sendMessage("#ircstuck", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0n by " + sender);
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
	                sendMessage("#hs_admin", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0ff by " + sender);
	                sendMessage("#hs_radio", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0ff by " + sender);
	                sendMessage("#hs_rp", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0ff by " + sender);
	                sendMessage("#hs_nsfw", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0ff by " + sender);
	                sendMessage("#ircstuck", Colors.BOLD + Colors.UNDERLINE + "requests have been turn 0ff by " + sender);
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
                if (input.equals("")){
                    sendMessage(channel, "please specify a s0ng name " + sender);
                } else {
                    sendMessage(channel, "y0ur s0ng request has been added t0 the list " + sender);
                    writeToFile(SONG_LIST, sender, input);
                }
            } else {
                sendMessage(channel, "requests are currently cl0sed " + sender);
            }
        }
    }
    
    //Get the time to wait for the reminder
    public double getPeriod(String periods, String regex) throws NumberFormatException {
        Pattern pattern = Pattern.compile("^.*?([\\d\\.]+)\\s*(?i:(" + regex + ")).*$");
        Matcher m = pattern.matcher(periods);
        m = pattern.matcher(periods);
        if (m.matches()) {
            double d = Double.parseDouble(m.group(1));
            if (d < 0 || d > 1e6) {
                throw new NumberFormatException("Number too large or negative (" + d + ")");
            }
            return d;
        }
        return 0;
    }
    
    //Run the reminder methods
    @SuppressWarnings("unchecked")
	public synchronized void run() {
        boolean running = true;
        while (running) {

            // If the list is empty, wait until something gets added.
            if (reminders.size() == 0) {
                try {
                    wait();
                }
                catch (InterruptedException e) {
                    // Do nothing.
                }
            }

            Reminder reminder = (Reminder) reminders.getFirst();
            long delay = reminder.getDueTime() - System.currentTimeMillis();
            if (delay > 0) {
                try {
                    wait(delay);
                }
                catch (InterruptedException e) {
                    // A new Reminder was added. Sort the list.
                    Collections.sort(reminders);
                    saveReminders();
                }
            }
            else {
                sendMessage(reminder.getChannel(), "hell0 " + reminder.getNick() + ", y0u asked me t0 remind y0u " + reminder.getMessage());
                reminders.removeFirst();
                saveReminders();
            }
            
        }
    }
    
    //Save any new reminders
    private void saveReminders() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(REMINDER_FILE)));
            out.writeObject(reminders);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            // If it doesn't work, no great loss!
        }
    }
    
    //Automatically load all reminds at start
    @SuppressWarnings("rawtypes")
	private void loadReminders() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(REMINDER_FILE)));
            reminders = (LinkedList) in.readObject();
            in.close();
        }
        catch (Exception e) {
            // If it doesn't work, no great loss!
        }
    }
    
    //Count number of lines in a file
	public static int countLines(String filename) throws IOException {
	    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
		int cnt = 0;
		@SuppressWarnings("unused")
		String lineRead = "";
		while ((lineRead = reader.readLine()) != null) {}
	
		cnt = reader.getLineNumber(); 
		reader.close();
		return cnt;
	}
	
	//Get a random quote from character
	@SuppressWarnings("resource")
	public String getQuote(String c, int num) throws IOException {
		String quote = "";
		if (c.equalsIgnoreCase("jade")) {
			int lines = countLines(JADE_QUOTES);
				if (num > lines || num <= 0) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JADE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("dave")) {
			int lines = countLines(DAVE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DAVE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("dirk")) {
			int lines = countLines(DIRK_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DIRK_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("rose")) {
			int lines = countLines(ROSE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ROSE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("roxy")) {
			int lines = countLines(ROXY_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ROXY_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("jane")) {
			int lines = countLines(JANE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JANE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("aradia")) {
			int lines = countLines(ARADIA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ARADIA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("aranea")) {
			int lines = countLines(ARANEA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ARANEA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("arquius") || c.equalsIgnoreCase("arquiusprite")) {
			int lines = countLines(ARQUIUS_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ARQUIUS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("caliborn")) {
			int lines = countLines(CALIBORN_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CALIBORN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("calliope")) {
			int lines = countLines(CALLIOPE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CALLIOPE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("calsprite") || c.equalsIgnoreCase("cal")) {
			int lines = countLines(CALSPRITE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CALSPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("cronus")) {
			int lines = countLines(CRONUS_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(CRONUS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("damara")) {
			int lines = countLines(DAMARA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DAMARA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("davesprite")) {
			int lines = countLines(DAVESPRITE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DAVESPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("dragon") || c.equalsIgnoreCase("dragonsprite")) {
			int lines = countLines(DRAGONSPRITE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DRAGONSPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("equius")) {
			int lines = countLines(EQUIUS_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(EQUIUS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("eridan")) {
			int lines = countLines(ERIDAN_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ERIDAN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("erisol") || c.equalsIgnoreCase("erisolsprite")) {
			int lines = countLines(ERISOL_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ERISOL_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("feferi")) {
			int lines = countLines(FEFERI_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(FEFERI_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("fefeta") || c.equalsIgnoreCase("fefetasprite")) {
			int lines = countLines(FEFETA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(FEFETA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("gamzee")) {
			int lines = countLines(GAMZEE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(GAMZEE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("horuss")) {
			int lines = countLines(HORUSS_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(HORUSS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("jake")) {
			int lines = countLines(JAKE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JAKE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("jasper") || c.equalsIgnoreCase("jaspersprite") || c.equalsIgnoreCase("jaspers")) {
			int lines = countLines(JASPER_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JASPER_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("john")) {
			int lines = countLines(JOHN_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JOHN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("kanaya")) {
			int lines = countLines(KANAYA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KANAYA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("kankri")) {
			int lines = countLines(KANKRI_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KANKRI_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("karkat")) {
			int lines = countLines(KARKAT_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KARKAT_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("kurloz")) {
			int lines = countLines(KURLOZ_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(KURLOZ_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("latula")) {
			int lines = countLines(LATULA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(LATULA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("meenah")) {
			int lines = countLines(MEENAH_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(MEENAH_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("meulin")) {
			int lines = countLines(MEULIN_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(MEULIN_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("mituna")) {
			int lines = countLines(MITUNA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(MITUNA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("nanna") || c.equalsIgnoreCase("nannasprite")) {
			int lines = countLines(NANNA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(NANNA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("nepeta")) {
			int lines = countLines(NEPETA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(NEPETA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("porrim")) {
			int lines = countLines(PORRIM_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(PORRIM_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("rufioh")) {
			int lines = countLines(RUFIOH_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(RUFIOH_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("sollux")) {
			int lines = countLines(SOLLUX_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(SOLLUX_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("tavros")) {
			int lines = countLines(TAVROS_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(TAVROS_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("terezi")) {
			int lines = countLines(TEREZI_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(TEREZI_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("vriska")) {
			int lines = countLines(VRISKA_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(VRISKA_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("doc") || c.equalsIgnoreCase("doc scratch")) {
			int lines = countLines(DOC_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(DOC_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("andrew") || c.equalsIgnoreCase("andrew hussie") || c.equalsIgnoreCase("hussie")) {
			int lines = countLines(ANDREW_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(ANDREW_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("jadesprite")) {
			int lines = countLines(JADESPRITE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(JADESPRITE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("squarewave")) {
			int lines = countLines(SQUAREWAVE_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(SQUAREWAVE_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (c.equalsIgnoreCase("hic") || c.equalsIgnoreCase(")(ic")) {
			int lines = countLines(HIC_QUOTES);
				if (num > lines || num < 1) {
					quote = "please enter a number between 1-" + lines;
				} else {
					try {
						FileInputStream fs= new FileInputStream(HIC_QUOTES);
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
						for(int i = 0; i < num-1; ++i)
						  br.readLine();
						quote = br.readLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
                return "there are n0 qu0tes f0r that character right n0w. please d0uble check y0ur spelling as well";
			}
		return Colors.TEAL + Colors.BOLD + "Quote #" + (num) + " - " + Colors.NORMAL + quote;
	}
	
	@SuppressWarnings("resource")
	public String getRandQuote(String c) throws IOException {
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
			}
		} else {
			return "there are n0 qu0tes f0r that character right n0w. please d0uble check y0ur spelling as well";
		}
			
		return Colors.TEAL + Colors.BOLD + "Quote #" + (quoteNum+1) + " - " + Colors.NORMAL + quote;
	}
    
    //Write user input to file
    public void writeToFile(String file, String sender, String msg) {
        try {
            Writer output = new BufferedWriter(new FileWriter(file, true));
            output.append(sender + ": " + msg + "\n");
            output.close();
        }
        catch (Exception e) {
            // If it doesn't work, no great loss!
        }
    }
    
    //Trim string (removes - Winamp from songname)
    public String trimString(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-9);
    }
    
    //Radomly choose 8ball outcome
    public static String getOutcome(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
    
    
    //Gets the weather with the given zipcode
    public String getWeather(String zip) {
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
            e.printStackTrace();
        }
        
        return Colors.NORMAL + Colors.BOLD + loc + " (" + zip + ")" + Colors.NORMAL + " - it is " + Colors.BOLD + tmp + "F " 
        + Colors.NORMAL + "0utside " + Colors.NORMAL + "(feels like " + Colors.BOLD + flik + "F" + Colors.NORMAL + ") and is " 
        + Colors.BOLD + cond + Colors.NORMAL + ".";
    }
    
    //Get Latest Page from MSPA RSS Feed
    public String getLatestPage() {
        String update = "";
        try {
            URL mspaXml = new URL("http://mspaintadventures.com/rss/rss.xml");
            InputStream xml = mspaXml.openStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < 1; temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    update = eElement.getElementsByTagName("link").item(0).getTextContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return update;
    }
    
    //Check for page update
    public static void checkUpdate() {
        try {
            URL mspaXml = new URL("http://mspaintadventures.com/rss/rss.xml");
            InputStream xml = mspaXml.openStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < 1; temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String update = eElement.getElementsByTagName("link").item(0).getTextContent();
                    int tempPage = Integer.parseInt(update.substring(41));
                    int tempPage1 = latestPage;
                    latestPage = tempPage;
                    if (latestPage > tempPage1) {
                        isUpdate = true;
                    } else {
                        isUpdate = false;
                    }
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Get the Current Song
    @SuppressWarnings("resource")
	public String getCurSong() {
        String curSong = "";
        FileInputStream fs;
		try {
			fs = new FileInputStream(CUR_SONG);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            curSong = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curSong;
    }
    
    //Check if a file exists
    public void checkFile(String fileLoc) {
        File file = new File(fileLoc);
        if (!file.exists()){
            try {
            	//Create file if it's not there
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //Check if sender is Op
    public boolean checkOp(String sender) {
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
    
    //Check if sender has Voice
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
    
    private Thread dispatchThread;
    @SuppressWarnings("rawtypes")
	private LinkedList reminders = new LinkedList();
    
}