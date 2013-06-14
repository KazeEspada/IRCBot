package com.iarekylew00t.ircbot.handlers;

import java.io.File;
import java.util.ArrayList;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import com.iarekylew00t.helpers.FileHelper;
import com.iarekylew00t.managers.DataManager;

public class RequestHandler implements Runnable{
	private boolean requests = false;
	private boolean running = false;
	private File REQ_FILE;
	private int interval;
	private ArrayList<String> reqList = new ArrayList<String>();
	private ArrayList<String> userList = new ArrayList<String>();
	private static Thread reqThread;
	private static PircBotX bot = DataManager.IRCbot;
	private static LogHandler logger = DataManager.logHandler;

	public RequestHandler() {
		requests = false;
		REQ_FILE = new File("songReq.txt");
		reqThread = new Thread(this);
	}
	
	public RequestHandler(File reqFile) {
		requests = false;
		REQ_FILE = reqFile;
		reqThread = new Thread(this);
	}
	
	@Override
	public void run() {
		if (running) {
			try {
				if (interval > 30) {
					Thread.sleep(1000 * 60 * (interval-30));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE OPEN FOR 30 MORE MINUTES ---");
					Thread.sleep(1000 * 60 * (interval-15));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE OPEN FOR 15 MORE MINUTES ---");
					Thread.sleep(1000 * 60 * (interval-5));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE OPEN FOR 5 MORE MINUTES ---");
					Thread.sleep(1000 * 60 * 5);
				} else if (interval > 15) {
					Thread.sleep(1000 * 60 * (interval-15));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE OPEN FOR 15 MORE MINUTES ---");
					Thread.sleep(1000 * 60 * (interval-5));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE OPEN FOR 5 MORE MINUTES ---");
					Thread.sleep(1000 * 60 * 5);
				} else if (interval > 5) {
					Thread.sleep(1000 * 60 * (interval-5));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE OPEN FOR 5 MORE MINUTES ---");
					Thread.sleep(1000 * 60 * 5);
				} else {
					Thread.sleep(1000 * 60 * interval);
				}
			} catch (InterruptedException e) {
				logger.error("THREAD INTERRUPTED", e);
			}
			turnOffRequests();
		} else if (!running){
			try {
				if (interval > 30) {
					Thread.sleep(1000 * 60 * (interval-30));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE TURNED OFF IN 30 MINUTES ---");
					Thread.sleep(1000 * 60 * (interval-15));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE TURNED OFF IN 15 MINUTES ---");
					Thread.sleep(1000 * 60 * (interval-5));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE TURNED OFF IN 5 MINUTES ---");
					Thread.sleep(1000 * 60 * 5);
				} else if (interval > 15) {
					Thread.sleep(1000 * 60 * (interval-15));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE TURNED OFF IN 15 MINUTES ---");
					Thread.sleep(1000 * 60 * (interval-5));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE TURNED OFF IN 5 MINUTES ---");
					Thread.sleep(1000 * 60 * 5);
				} else if (interval > 5) {
					Thread.sleep(1000 * 60 * (interval-5));
					sendToAllChannels(Colors.BOLD + Colors.OLIVE + "--- REQUESTS WILL BE TURNED OFF IN 5 MINUTES ---");
					Thread.sleep(1000 * 60 * 5);
				} else {
					Thread.sleep(1000 * 60 * interval);
				}
			} catch (InterruptedException e) {
				logger.error("THREAD INTERRUPTED", e);
			}
			turnOffRequests();
		}
	}

    private void sendToAllChannels(String message) {
    	Channel[] channels = bot.getChannels().toArray(new Channel[0]);
    	for (int i = 0; i < channels.length; i++) {
    		bot.sendMessage(channels[i], message);
    	}
    }
	
	public void turnOnRequests() {
		if (!REQ_FILE.exists()) {
			FileHelper.createFile(REQ_FILE);
		}
		requests = true;
		running = true;
		logger.notice("REQUESTS HAVE BEEN SET TURNED ON");
		sendToAllChannels(Colors.BOLD + Colors.GREEN + "--- REQUESTS HAVE BEEN TURNED ON ---");
	}
	
	public void turnOnRequests(int time) {
		interval = time;
		if (!REQ_FILE.exists()) {
			FileHelper.createFile(REQ_FILE);
		}
		requests = true;
		running = true;
		logger.notice("REQUESTS HAVE BEEN SET TURNED ON FOR " + time + " MIUNTES");
		sendToAllChannels(Colors.BOLD + Colors.GREEN + "--- REQUESTS HAVE BEEN TURNED ON FOR " + time + " MINUTES ---");
		reqThread.start();
	}
	
	public void turnOffRequests() {
		running = false;
		requests = false;
		reqList.clear();
		userList.clear();
		logger.notice("REQUESTS HAVE BEEN SET TURNED OFF");
		sendToAllChannels(Colors.BOLD + Colors.RED + "--- REQUESTS HAVE BEEN TURNED OFF ---");
	}
	
	public void turnOffRequests(int time) {
		interval = time;
		running = false;
		logger.notice("REQUESTS HAVE BEEN SET TURNED OFF IN " + time + " MINUTES");
		sendToAllChannels(Colors.BOLD + Colors.RED + "--- REQUESTS WILL BE TURNED OFF IN " + time + " MINUTES ---");
		reqThread.start();
	}
	
	public boolean areOpen() {
		return requests;
	}
	
	public void addRequest(String song, User user) {
		reqList.add(song.toLowerCase());
		FileHelper.writeToFile(REQ_FILE, user.getNick() + ": " + song, true);
		logger.debug("ADDED \"" + song + "\" TO REQ LIST");
		userList.add(user.getHostmask());
		logger.debug("ADDED \"" + user.getNick() + "@" + user.getHostmask() + "\" TO USER LIST");
	}
	
	public boolean hasBeenRequested(String song) {
		for (int i = 0; i < reqList.size(); i++) {
			if (reqList.get(i).toLowerCase().contains(song.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public int countUserReqests(String name) {
		int numOfReq = 0;
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).equals(name)) {
				numOfReq++;
			}
		}
		return numOfReq;
	}

	public void clearRequests() {
		REQ_FILE.delete();
	}
}
