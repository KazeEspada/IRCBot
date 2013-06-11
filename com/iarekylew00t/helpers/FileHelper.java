package com.iarekylew00t.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public final class FileHelper {
	private static LogHandler logger = DataManager.logHandler;

	private FileHelper() {
		throw new AssertionError();
	}
	
	public static void createFile(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			logger.error("COULD NOT CREATE FILE \"" + file.getName() + "\"", e);
		}
	}
	
    public static void writeToFile(File file, String message, boolean append) {
        try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
        	writer.write(message + "\n");
        	writer.close();
        }
        catch (Exception e) {
			logger.error("COULD NOT WRITE TO FILE \"" + file.getName() + "\"", e);
        }
    }	
    
	public static int countLines(File file) {
		int lines = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();
		} catch (Exception e) {
			logger.error("COULD NOT COUNT LINES IN FILE \"" + file.getName() + "\"", e);
		}
		return lines;
	}
	
	public static String readLine(File file, int line) {
		int numOfLines = countLines(file);
		String text = "";
		if (line > numOfLines || line <= 0) {
			return "Please enter a number from 1-" + numOfLines;
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			for (int i = 1; i < line; i++) {
				reader.readLine();
			}
			text = reader.readLine();
			reader.close();
		} catch (Exception e) {
			logger.error("COULD NOT READ LINE " + line + " IN FILE \"" + file.getName() + "\"", e);
		}
		return text;
	}
	
	public static String searchFile(File file, String search) {
		String line, searchLine = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				if (line.toLowerCase().contains(search)) {
					searchLine = line;
					reader.close();
					return searchLine;
				}
			}
			reader.close();
		} catch (Exception e) {
			logger.error("COULD NOT SEARCH IN FILE \"" + file.getName() + "\"", e);
		}
		return searchLine;
	}

}
