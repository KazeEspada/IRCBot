package com.iarekylew00t.ircbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.iarekylew00t.ircbot.LogHandler;

public class SongManager implements Runnable {
	private String curSong, prevSong = null, tempSong = null;
	private String CUR_SONG;
	private LogHandler logger = new LogHandler();
	private Thread songThread;
	
	public SongManager() {
		CUR_SONG = "curSong.txt";
		checkFile(CUR_SONG);
		songThread = new Thread(this);
		songThread.start();
	}
	
	public SongManager(String fileLoc) {
		CUR_SONG = fileLoc;
		checkFile(CUR_SONG);
		songThread = new Thread(this);
		songThread.start();
	}
	
	public void run() {
    	while (true) {
    		try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
    		updateCurSong();
    		updatePrevSong();
    	}
	}
	
    private String trimString(String s, int num) {
        if (s == null || s.length() == 0) {
            return s;
        }
        s = s.substring(0, s.length()-num);
        s = s.replaceFirst("[0-9]+. ", "");
        s = s.replace("?/?", "/");
        return s;
    }
    
    private void updatePrevSong() {
		if (!curSong.equalsIgnoreCase(tempSong)) {
			prevSong = tempSong;
			tempSong = curSong;
			logger.debug("--- SUCCESSFULLY CHANGED PREVIOUS SONG ---");
		}
    }
    
    public String getPrevSong() {
    	return prevSong;
    }
    
    public String getCurSong() {
    	return curSong;
    }
    
	@SuppressWarnings("resource")
	public void updateCurSong() {
        FileInputStream fs;
		try {
			fs = new FileInputStream(CUR_SONG);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			br.readLine(); //Skips first line
			curSong = br.readLine();
		} catch (Exception e) {
			logger.error(e);
		}
		curSong = trimString(curSong, 9);
    }
    
	private void checkFile(String fileLoc) {
        File file = new File(fileLoc);
        if (!file.exists()){
            try {
            	//Create file if it's not there
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
