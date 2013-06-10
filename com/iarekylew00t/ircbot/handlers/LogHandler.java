package com.iarekylew00t.ircbot.handlers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.iarekylew00t.helpers.FileHelper;
import com.iarekylew00t.managers.DataManager;


public class LogHandler {
	private File LOG_FILE;
	private File logDir = new File("./logs/");
	private DateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS");
	private DateFormat backupLog = new SimpleDateFormat("dd_MMM_yy[HH-mm-ssSSS]");
	private Date date;
	private String logTime;
	private boolean debug;
	
	public LogHandler() {
		debug = false;
		LOG_FILE = new File("log.log");
		if (!LOG_FILE.exists()) {
			FileHelper.createFile(LOG_FILE);
		}
		long diff = new Date().getTime() - FileHelper.checkFileCreation(LOG_FILE).toMillis();
		if (diff > 3 * 24 * 60 * 60 * 1000) {
			date = new Date();
			if (!logDir.exists()) {
				logDir.mkdirs();
			}
			String fileNameTime = backupLog.format(date).toUpperCase();
			FileHelper.copyFile(LOG_FILE, new File("./logs/" + fileNameTime + LOG_FILE.getName()));
			FileHelper.recreateFile(LOG_FILE);
		}
	}

	public LogHandler(String fileLoc) {
		debug = false;
		LOG_FILE = new File(fileLoc);
		if (!LOG_FILE.exists()) {
			FileHelper.createFile(LOG_FILE);
		}
		long diff = new Date().getTime() - FileHelper.checkFileCreation(LOG_FILE).toMillis();
		if (diff > 3 * 24 * 60 * 60 * 1000) {
			date = new Date();
			if (!logDir.exists()) {
				logDir.mkdirs();
			}
			String fileNameTime = backupLog.format(date);
			FileHelper.copyFile(LOG_FILE, new File("./logs/" + fileNameTime + LOG_FILE.getName()));
			FileHelper.recreateFile(LOG_FILE);
		}
	}
	
	public void setLogFile(File file) {
		LOG_FILE = file;
	}
	
	public void log(String log) {
		date = new Date();
		logTime = logFormat.format(date) + " ";
		//Remove all non-ASCII characters 
		log = log.replaceAll("[^\\x20-\\x7e]", "");
		System.out.println(logTime + log);
		FileHelper.writeToFile(LOG_FILE, logTime + log, true);
	}
	
	public void log(Exception e) {
		if (debug) {
			System.out.println();
			e.printStackTrace();
			FileHelper.writeToFile(LOG_FILE, "" + e, true);
			DataManager.exception = e;
		}
	}
	
	public void debug(String log) {
		if (debug) {
			log("[DEBUG]: --- " + log + " ---");
		}
	}
	
	public void info(String log) {
		log("[INFO]: " + log);
		
	}
	
	public void warning(String log) {
		log("[WARNING]: ***** " + log + " *****");
	}
	
	public void error(String log) {
		log("[ERROR]: ===== " + log + " =====");
	}

	public void error(Exception e) {
		System.out.println();
		log(e);
	}
	
	public void error(String error, Exception e) {
		log("[ERROR]: ===== " + error + " =====");
		log(e);
	}
	
	public void notice(String log) {
		log("[NOTICE]: " + log);
		
	}
	
	public void enableDebug(boolean value) {
		debug = value;
	}
}
