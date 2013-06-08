package com.iarekylew00t.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.*;

import com.iarekylew00t.managers.DataManager;

public final class FileHelper {

	private FileHelper() {
		throw new AssertionError();
	}

	public static boolean checkFile(File file) {
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	public static void createFile(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(File file) {
		file.delete();
	}
	
	public static void recreateFile(File file) {
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static FileTime checkFileCreation(File file) {
		try {
			BasicFileAttributes view = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class).readAttributes();
			return view.creationTime();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void copyFile(File copyFrom, File copyTo) {
		try {
			FileInputStream in = new FileInputStream(copyFrom);
			FileOutputStream out = new FileOutputStream(copyTo);
			byte[] buffer = new byte[4096];
			int len;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
    public static void writeToFile(File file, String message, boolean append) {
        try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
        	writer.write(message + "\n");
        	writer.close();
        }
        catch (Exception e) {
        	e.printStackTrace();
			DataManager.exception = e;
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
			e.printStackTrace();
			DataManager.exception = e;
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
			e.printStackTrace();
			DataManager.exception = e;
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
			e.printStackTrace();
			DataManager.exception = e;
		}
		return searchLine;
	}

}
