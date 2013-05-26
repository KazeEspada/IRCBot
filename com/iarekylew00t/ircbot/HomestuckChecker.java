package com.iarekylew00t.ircbot;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HomestuckChecker implements Runnable {
	private Thread updateThread;
	private PircBot mainBot;
	private LogHandler logger = new LogHandler();
	private boolean isUpdate;
	private int curPage, interval = 15;

	public HomestuckChecker(PircBot bot) {
		checkUpdate();
		mainBot = bot;
		updateThread = new Thread(this);
		updateThread.start();
	}
	
	public HomestuckChecker(PircBot bot, int min) {
		interval = min;
		checkUpdate();
		mainBot = bot;
		updateThread = new Thread(this);
		updateThread.start();
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * interval);
			} catch (InterruptedException e) {
				logger.error(e);
			}
			checkUpdate();
			
			if (isUpdate) {
				sendToAllChannels(Colors.GREEN + Colors.BOLD + "-- THERE IS AN UPDATE --");
				logger.notice("*** THERE IS AND UPDATE ***");
			}
		}
	}	
	
	public boolean manualCheck() {
    	int latestPage = getLatestPage();
    	if (latestPage > curPage) {
        	curPage = latestPage;
            return true;
    	} else {
    		return false;
        }
	}

    private void sendToAllChannels(String message) {
    	String[] channels = mainBot.getChannels();
    	for (int i = 0; i < channels.length; i++) {
    		mainBot.sendMessage(channels[i], message);
    	}
    }
	
    private void checkUpdate() {
		int latestPage = getLatestPage();
		if (latestPage > curPage) {
	    	curPage = latestPage;
	        isUpdate = true;
		} else {
	        isUpdate = false;
	    }
		logger.debug("--- SUCCESSFULLY CHECKED FOR UPDATE ---");
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
			logger.error("COULD NOT GRAB LATEST PAGE");
            logger.error(e);
        }
        
        return Integer.parseInt(update.substring(41));
    }
    
    public int getPage() {
    	int pageNum = getLatestPage();
		int randNum = new Random().nextInt(pageNum-1900);
		while (randNum == 4299 || randNum == 4938 || randNum == 4988) {
			randNum = new Random().nextInt(pageNum);
		}
    	return 1900 + randNum;
    }
    
    public int getPage(int page) {
    	int pageNum = getLatestPage();
    	if (page > (pageNum-1900) || page <= 0) {
    		return pageNum-1900;
    	}
    	return 1900 + page;
    }
}
