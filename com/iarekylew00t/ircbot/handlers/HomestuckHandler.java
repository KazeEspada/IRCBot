package com.iarekylew00t.ircbot.handlers;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iarekylew00t.helpers.Downloader;
import com.iarekylew00t.helpers.FileHelper;
import com.iarekylew00t.managers.DataManager;


public class HomestuckHandler implements Runnable {
	private int curPage, interval = 15;
	private String MSPAWIKI_SEARCH = "http://mspaintadventures.wikia.com/wiki/index.php?search=";
	private String FILE_BASE = "https://raw.github.com/IAreKyleW00t/IRCBot/master/files/";
	private File HS_LINKS = new File("./files/hs_links.txt");
	private static Thread updateThread;
	private static PircBotX bot = DataManager.IRCbot;
	private static LogHandler logger = DataManager.logHandler;

	public HomestuckHandler() {
		if (!HS_LINKS.exists()) {
			try {
				Downloader.downloadFile(new URL(FILE_BASE + HS_LINKS.getName()), HS_LINKS);
			} catch (MalformedURLException e) {
				logger.error("COULD NOT DOWNLOAD HS_LINKS FROM \"" + FILE_BASE + HS_LINKS.getName() + "\"", e);
			}
		}
		interval = 15;
		checkUpdate();
		updateThread = new Thread(this);
		updateThread.start();
	}
	
	public HomestuckHandler(int min) {
		if (!HS_LINKS.exists()) {
			try {
				Downloader.downloadFile(new URL(FILE_BASE + HS_LINKS.getName()), HS_LINKS);
			} catch (MalformedURLException e) {
				logger.error("COULD NOT DOWNLOAD HS_LINKS FROM \"" + FILE_BASE + HS_LINKS.getName() + "\"", e);
			}
		}
		interval = min;
		checkUpdate();
		updateThread = new Thread(this);
		updateThread.start();
	}
	
	@Override
	public void run(){
		while (true) {
			try {
				Thread.sleep(1000 * 60 * interval);
			} catch (InterruptedException e) {
				logger.error("THREAD INTERRUPTED", e);
			}
			if (checkUpdate()) {
				sendToAllChannels(Colors.GREEN + Colors.BOLD + "-- THERE IS AN UPDATE --");
				logger.notice("THERE IS AND UPDATE");
			}
		}
	}

    private void sendToAllChannels(String message) {
    	Channel[] channels = bot.getChannels().toArray(new Channel[0]);
    	for (int i = 0; i < channels.length; i++) {
    		bot.sendMessage(channels[i], message);
    	}
    }
	
    public boolean checkUpdate() {
		int latestPage = getLatestPage();
		if (latestPage > curPage) {
	    	curPage = latestPage;
	        return true;
		}
		return false;
    }
	
    public int getLatestPage(){
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
			logger.error("COULD NOT GRAB LATEST PAGE", e);
        }
        
        return Integer.parseInt(update.substring(41));
    }
    
    public int getPage() {
    	int pageNum = getLatestPage();
    	Random rand = new Random();
		int randNum = rand.nextInt((pageNum-1900 - 1901) + 1) + 1901;
		while (randNum == 4299 || randNum == 4938 || randNum == 4988) {
			randNum = rand.nextInt(pageNum-1900);
		}
    	return 1900 + randNum;
    }
    
    public int getPage(int page) {
    	int pageNum = getLatestPage();
    	if (page > (pageNum-1900)) {
    		return pageNum;
    	} else if (page <= 0) {
    		return 1901;
    	}
    	return 1900 + page;
    }
	
	public String searchWiki(String search) {
		search = search.replace(' ','_');
		return MSPAWIKI_SEARCH + search;
	}
	
	public String searchPage(String search) {
		if (!HS_LINKS.exists()) {
			try {
				Downloader.downloadFile(new URL(FILE_BASE + HS_LINKS.getName()), HS_LINKS);
			} catch (MalformedURLException e) {
				logger.error("COULD NOT DOWNLOAD HS_LINKS FROM \"" + FILE_BASE + HS_LINKS.getName() + "\"", e);
			}
		}
    	String line = FileHelper.searchFile(HS_LINKS, search);
    	if (line.equals(null)) {
    		return "c0uld n0t find any page with \"" + search + "\" in it";
    	}
    	String[] lineSplit;
    	lineSplit = line.split(" >>> ");
		return lineSplit[1] + ": " + lineSplit[0];
    }
}
