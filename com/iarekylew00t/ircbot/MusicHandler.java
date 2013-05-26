package com.iarekylew00t.ircbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;

import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.ircbot.LogHandler;

public class MusicHandler implements Runnable {
	private String curSong, prevSong = null, tempSong = null, curTime;
	private String CURSONG_FILE;
	private LogHandler logger = new LogHandler();
	private Thread songThread;
	private PircBot mainBot;
	private EmailClient emailClient;
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public MusicHandler(PircBot bot, EmailClient email) {
		mainBot = bot;
		emailClient = email;
		CURSONG_FILE = "curSong.txt";
		checkFile(CURSONG_FILE);
		songThread = new Thread(this);
		songThread.start();
	}
	
	public MusicHandler(String fileLoc, PircBot bot, EmailClient email) {
		mainBot = bot;
		emailClient = email;
		CURSONG_FILE = fileLoc;
		checkFile(CURSONG_FILE);
		songThread = new Thread(this);
		songThread.start();
	}
	
	public void run() {
    	while (true) {
    		try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
    		updateCurSong();
    		checkWinamp();
    		updatePrevSong();
    	}
	}
	
    private String trimString(String s, int num) {
        if (s == null || s.length() == 0) {
            return s;
        }
        s = s.substring(0, s.length()-num);
        s = s.replaceFirst("[0-9]+. ", "");
        s = s.replace("?/?", "/");
        return s;
    }

	@SuppressWarnings("unused")
	private void restartWinamp() throws Exception {
		Date date = new Date();
    	curTime = dateFormat.format(date);
        mainBot.sendMessage("#hs_radio", Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Winamp appears to be malfunctioning - Attempting repair");
        mainBot.sendMessage("#hs_radio", Colors.BOLD + "----------- RESTARTING WINAMP -----------");
        logger.warning("RESTARTING WINAMP");
        
		Runtime.getRuntime().exec("taskkill /IM winamp.exe");
		Thread.sleep(1500);
		Process p = Runtime.getRuntime().exec("winamp.exe");
		
        mainBot.sendMessage("#hs_radio", Colors.BOLD + Colors.GREEN + "----- WINAMP RESTARTED SUCCESSFULLY -----");
		emailClient.sendEmail("kyle10468@gmail.com", "NOTICE: Winamp Restarted", "Winamp restarted succesfully @ " + curTime);
        logger.notice("WINAMP RESTARTED SUCCESSFULLY");
    }
	
	@SuppressWarnings("unused")
	public void restartWinamp(String channel, String sender) throws Exception {
		Date date = new Date();
    	curTime = dateFormat.format(date);
        mainBot.sendMessage(channel, Colors.BOLD + "----------- RESTARTING WINAMP -----------");
        logger.warning("RESTARTING WINAMP");
        
		Runtime.getRuntime().exec("taskkill /IM winamp.exe");
		Thread.sleep(1500);
		Process p = Runtime.getRuntime().exec("winamp.exe");
		
        mainBot.sendMessage(channel, Colors.BOLD + Colors.GREEN + "----- WINAMP RESTARTED SUCCESSFULLY -----");
		emailClient.sendEmail("kyle10468@gmail.com", "NOTICE: Winamp Restarted", "Winamp restarted succesfully @ " + curTime + " by: " + sender);
        logger.notice("WINAMP RESTARTED SUCCESSFULLY");
    }
    
    private void updatePrevSong() {
		if (!curSong.equalsIgnoreCase(tempSong)) {
			prevSong = tempSong;
			tempSong = curSong;
			logger.debug("--- SUCCESSFULLY CHANGED PREVIOUS SONG ---");
		}
    }
    
    private void checkWinamp() {
    	if (curSong.contains("??????")) {
    		try {
				restartWinamp();
				updateCurSong();
			} catch (Exception e) {
				try {
					mainBot.sendMessage("hs_radio", Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Failed to restart Winamp - Notifying IAreKyleW00t");
					emailClient.sendEmail("kyle10468@gmail.com", "WARNING: Winamp Failed to Restart", "Winamp FAILED to resatrt @ " + curTime);
				} catch (MessagingException e1) {
					mainBot.sendMessage("hs_radio", Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not send Email - please notify IAreKyleW00t: http://iarekylew00t.tumblr.com/ask");
					logger.error("FAILED TO SEND EMAIL");
					logger.error(e1);
				}
				logger.error("FAILED TO RESTART WINAMP");
				logger.error(e);
			}
    	}
    	updateCurSong();
    }
    
    public String getPrevSong() {
    	updatePrevSong();
    	return prevSong;
    }
    
    public String getCurSong() {
    	updateCurSong();
    	return curSong;
    }
    
	@SuppressWarnings("resource")
	private void updateCurSong() {
        FileInputStream fs;
		try {
			fs = new FileInputStream(CURSONG_FILE);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			br.readLine(); //Skips first line
			curSong = br.readLine();
		} catch (Exception e) {
			logger.error(e);
		}
		curSong = trimString(curSong, 9);
    }
    
	private void checkFile(String fileLoc) {
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
}
