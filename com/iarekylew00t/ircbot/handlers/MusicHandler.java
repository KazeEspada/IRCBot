package com.iarekylew00t.ircbot.handlers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.helpers.FileHelper;
import com.iarekylew00t.managers.DataManager;

public class MusicHandler implements Runnable {
	private String curSong, prevSong = null, tempSong = null, curTime;
	private File CURSONG_FILE = new File("./files/curSong.txt"), PREVSONG_FILE = new File("./files/prevSong.txt");
	private LogHandler logger = DataManager.logHandler;
	private Thread songThread;
	private EmailClient emailClient = DataManager.emailClient;
	private PircBotX bot = DataManager.IRCbot;
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
	
	public MusicHandler() {
		if (!CURSONG_FILE.exists()) {
			FileHelper.createFile(CURSONG_FILE);
		}
		if (!PREVSONG_FILE.exists()) {
			FileHelper.createFile(PREVSONG_FILE);
		}
		tempSong = getCurSong();
		songThread = new Thread(this);
		songThread.start();
	}
	
	@Override
	public void run() {
    	while (true) {
    		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
    		updateCurSong();
    		checkWinamp();
    		updatePrevSong();
    	}
	}
	
	public void restartWinamp(Channel channel, User sender) throws Exception {
		Date date = new Date();
    	curTime = dateFormat.format(date);
        bot.sendMessage(channel, Colors.BOLD + "----------- RESTARTING WINAMP -----------");
        logger.warning("RESTARTING WINAMP");
		Runtime.getRuntime().exec("taskkill /IM winamp.exe");
		Thread.sleep(1500);
		Runtime.getRuntime().exec("winamp.exe");
        bot.sendMessage(channel, Colors.BOLD + Colors.GREEN + "----- WINAMP RESTARTED SUCCESSFULLY -----");
        logger.warning("WINAMP RESTARTED SUCCESSFULLY");
        emailClient.sendEmail("kyle10468@gmail.com", "NOTICE: Winamp Restarted", "Winamp restarted succesfully @ " + curTime + " by: " + sender.getNick());
    }

	private void restartWinamp() throws Exception {
		Date date = new Date();
    	curTime = dateFormat.format(date);
        bot.sendMessage("#hs_radio", Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Winamp appears to be malfunctioning - Attempting repair");
        bot.sendMessage("#hs_radio", Colors.BOLD + "----------- RESTARTING WINAMP -----------");
        logger.warning("RESTARTING WINAMP");
		Runtime.getRuntime().exec("taskkill /IM winamp.exe");
		Thread.sleep(1500);
		Runtime.getRuntime().exec("winamp.exe");
        bot.sendMessage("#hs_radio", Colors.BOLD + Colors.GREEN + "----- WINAMP RESTARTED SUCCESSFULLY -----");
        logger.warning("WINAMP RESTARTED SUCCESSFULLY");
        emailClient.sendEmail("kyle10468@gmail.com", "NOTICE: Winamp Restarted", "Winamp restarted succesfully @ " + curTime);
    }
    
    private void checkWinamp() {
    	if (curSong.contains("??????")) {
    		logger.error("WINAMP ERROR DETECTED");
    		try {
				restartWinamp();
				updateCurSong();
			} catch (Exception e) {
				bot.sendMessage("#hs_radio", Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Failed to restart Winamp - Notifying IAreKyleW00t...");
				logger.error("FAILED TO RESTART WINAMP", e);
				bot.sendMessage("#hs_radio", "Please notify IAreKyleW00t manually to make sure he knows: http://iarekylew00t.tumblr.com/ask");
				emailClient.sendEmail("kyle10468@gmail.com", "WARNING: Winamp Failed to Restart", "Winamp FAILED to restart @ " + curTime);
				DataManager.exception = e;
			}
    	}
    	updateCurSong();
    }
    
    private void updatePrevSong() {
		if (!curSong.equalsIgnoreCase(tempSong)) {
			prevSong = tempSong;
			FileHelper.writeToFile(PREVSONG_FILE, prevSong, false);
			tempSong = curSong;
		}
    	try {
			prevSong = FileHelper.readLine(PREVSONG_FILE, 1);
		} catch (Exception e) {
			logger.error(e);
			DataManager.exception = e;
		}
    }
    
	private void updateCurSong() {
		try {
			curSong = FileHelper.readLine(CURSONG_FILE, 2);
		} catch (Exception e) {
			logger.error(e);
			DataManager.exception = e;
		}
		curSong = trimString(curSong, 9);
    }

    public String getPrevSong() {
    	updatePrevSong();
    	return prevSong;
    }
    
    public String getCurSong() {
    	updateCurSong();
    	return curSong;
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
}
