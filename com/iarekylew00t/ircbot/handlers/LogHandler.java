package com.iarekylew00t.ircbot.handlers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.iarekylew00t.helpers.FileHelper;


public class LogHandler {
	private File LOG_FILE;
	private File logDir = new File("./logs/");
	private DateFormat logFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private DateFormat backupLog = new SimpleDateFormat("dd-MMM-yy_[hh-mm-ssSSS]");
	private Date date;
	private String logTime;
	
	public LogHandler() {
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
			String fileNameTime = backupLog.format(date);
			FileHelper.copyFile(LOG_FILE, new File("./logs/" + fileNameTime + LOG_FILE.getName()));
			FileHelper.recreateFile(LOG_FILE);
		}
	}

	public LogHandler(String fileLoc) {
		LOG_FILE = new File(fileLoc);
		if (!LOG_FILE.exists()) {
			FileHelper.createFile(LOG_FILE);
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
		e.printStackTrace();
		FileHelper.writeToFile(LOG_FILE, "" + e, true);
	}
	
	public void debug(String log) {
		log("[DEBUG]: --- " + log + " ---");
		
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
		log(e);
	}
	
	public void error(String error, Exception e) {
		log("[ERROR]: ===== " + error + " =====");
		log(e);
	}
	
	public void notice(String log) {
		log("[NOTICE]: " + log);
		
	}
}
