package com.iarekylew00t.ircbot.handlers;

import java.io.File;
import java.util.ArrayList;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import com.iarekylew00t.helpers.FileHelper;
import com.iarekylew00t.managers.DataManager;

public class RequestHandler {
	private LogHandler logger = DataManager.logHandler;
	private PircBotX bot = DataManager.IRCbot;
	private boolean requests = false;
	private File REQ_FILE;
	private ArrayList<String> reqList = new ArrayList<>();
	private ArrayList<String> userList = new ArrayList<>();

	public RequestHandler() {
		requests = false;
		REQ_FILE = new File("songReq.txt");
	}
	
	public RequestHandler(File reqFile) {
		requests = false;
		REQ_FILE = reqFile;
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
		logger.notice("REQUESTS HAVE BEEN SET TURNED ON");
		sendToAllChannels(Colors.BOLD + Colors.GREEN + "--- REQUESTS HAVE BEEN TURNED ON ---");
	}
	
	public void turnOffRequests() {
		requests = false;
		reqList.clear();
		userList.clear();
		logger.notice("REQUESTS HAVE BEEN SET TURNED OFF");
		sendToAllChannels(Colors.BOLD + Colors.RED + "--- REQUESTS HAVE BEEN TURNED OFF ---");
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
		if (reqList.contains(song.toLowerCase())) {
			return true;
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
