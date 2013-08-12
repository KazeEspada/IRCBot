package com.iarekylew00t.ircbot.handlers;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iarekylew00t.managers.DataManager;

public class RssHandler implements Runnable {
	private int interval;
	private long currentPost;
	private static LogHandler logger = DataManager.logHandler;
	private static Thread feedThread;
	private static URL feedURL;
	private static PircBotX bot = DataManager.IRCbot;

	public RssHandler(String feed){
		interval = 15;
		try {
			feedURL = new URL(feed);
			currentPost = getLatestPostTime();
		} catch (Exception e) {
			logger.error("COULD NOT PARSE DATA FROM XML", e);
		}
		feedThread = new Thread(this);
		feedThread.start();
	}
	
	public RssHandler(String feed, int min){
		interval = min;
		try {
			feedURL = new URL(feed);
			currentPost = getLatestPostTime();
		} catch (Exception e) {
			logger.error("MALFORMED URL", e);
		}
		feedThread = new Thread(this);
		feedThread.start();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * interval);
			} catch (InterruptedException e) {
				logger.error("COULD NOT PARSE DATA FROM XML", e);
			}
			if (hasNewPost()) {
				notifyAllChannels();
			}
		}
	}
	
	public String getLatest() throws Exception {
        InputStream xml = feedURL.openStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String postTitle = eElement.getElementsByTagName("title").item(0).getTextContent();
                String postURL = eElement.getElementsByTagName("link").item(0).getTextContent();
                logger.debug("getLatest()=" + postTitle + " - " + postURL);
                return postTitle + " - " + postURL;
            }
		return null;
	}
	
	private boolean hasNewPost() {
		try {
			long latestPost = getLatestPostTime();
			if (latestPost > currentPost) {
				logger.notice("THERE IS A NEW POST");
				currentPost = latestPost;
		        return true;
			}
		} catch (Exception e) {
			logger.error("COULD NOT GET LATEST POST TIME", e);
		}
		return false;
	}
	
	private long getLatestPostTime() throws Exception {
		DateFormat input = new SimpleDateFormat("EEE, d MMM yyyy H:m:s z");
        InputStream xml = feedURL.openStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Date publishTime = input.parse(eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                logger.debug("getLastUpdatedTime()=" + publishTime.getTime());
                return publishTime.getTime();
            }
		return Integer.MIN_VALUE;
	}
	
    public void notifyAllChannels() {
    	Channel[] channels = bot.getChannels().toArray(new Channel[0]);
    	for (int i = 0; i < channels.length; i++) {
    		try {
				bot.sendMessage(channels[i], Colors.OLIVE + Colors.BOLD + getLatest());
			} catch (Exception e) {
				bot.sendMessage("skaianet_chat", DataManager.ERROR + "Could not parse data from XML");
				logger.error("COULD NOT PARSE DATA FROM XML", e);
			}
    	}
    }
}
