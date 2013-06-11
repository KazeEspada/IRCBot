package com.iarekylew00t.helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public final class Downloader {
	private static LogHandler logger = DataManager.logHandler;

	private Downloader() {
		throw new AssertionError();
	}
	
	public static void downloadFile(URL url, File saveAs) {
		BufferedInputStream in = null;
		FileOutputStream out = null;
		logger.notice("DOWNLOADING FILE: \"" + url + "\"");
		try {
			in = new BufferedInputStream(url.openStream());
			out = new FileOutputStream(saveAs);
			byte[] data = new byte[2048];
			int count;
			while ((count = in.read(data, 0, 2048)) > 0) {
				out.write(data, 0, count);
			}
			System.out.println();
			in.close();
			out.close();
		} catch (IOException e) {
			logger.error("COULD NOT DOWNLOAD FILE from \"" + url + "\"", e);
		}
	}
	
	public static int getFileSize(URL url) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			conn.getInputStream();
			int size = conn.getContentLength();
			conn.disconnect();
			return size;
		} catch (IOException e) {
			logger.error("COULD NOT GET FILE SIZE", e);
		}
		return -1;
	}
}
