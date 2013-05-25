package com.iarekylew00t.ircbot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHandler {
	private String VER = "0.0.1.2";
	private File LOG_FILE;
	private DateFormat logFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private Date date;
	private String logTime;
	
	public LogHandler() {
		LOG_FILE = new File("log.log");
		try {
			checkFile(LOG_FILE);
		} catch (IOException e) {
			this.error("Could not create 'LOG_FILE'");
		}
	}

	public LogHandler(String fileLoc) {
		LOG_FILE = new File(fileLoc);
		try {
			checkFile(LOG_FILE);
		} catch (IOException e) {
			this.error("Could not create 'LOG_FILE'");
		}
	}
	
	public void setLogFile(File file) {
		LOG_FILE = file;
	}
	
	public void log(String log) {
		date = new Date();
		logTime = logFormat.format(date) + " ";
		String cleanLog = log.replace("[^\\x00-\\x7F]", "");
		System.out.println(logTime + cleanLog);
		writeToFile(logTime + cleanLog);
	}
	
	public void debug(String log) {
		log("[DEBUG]: ----- " + log + " -----");
		
	}
	
	public void warning(String log) {
		log("[WARNING]: ----- " + log + " -----");
	}
	
	public void error(String log) {
		log("[ERROR]: ===== " + log + " =====");
	}
	
	public void notice(String log) {
		log("[NOTICE]: " + log);
		
	}
	
    private void writeToFile(String message) {
        try {
            Writer output = new BufferedWriter(new FileWriter(LOG_FILE, true));
            output.append(message + "\n");
            output.close();
        }
        catch (Exception e) {
        	this.error("");
        }
    }
    
	private void checkFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	public String getVersion() {
		return VER;
	}
}
