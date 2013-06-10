package com.iarekylew00t.google;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class Google {
	private static final String GOOGL_BASE = "https://www.googleapis.com/urlshortener/v1/url";
	private static final String GOOGLE_BASE = "http://www.google.com/";
	private static final String YOUTUBE_BASE = "http://www.youtube.com/";
	private String apiKey;
	private LogHandler logger = DataManager.logHandler;
	
	public Google(String api) {
        if (api.isEmpty()) {
        	logger.error("PLEASE USE A VALID APIKEY");
            throw new IllegalArgumentException("APIKey must be specified, see the Google API docs: http://goo.gl/2rfGn");
        }
        apiKey = api;
	}

	public String googleSearch(String search) {
		search = search.replace(' ','+');
		return GOOGLE_BASE + "search?q=" + search;
	}
	
	public String youtubeSearch(String search) {
		search = search.replace(' ','+');
		return YOUTUBE_BASE + "results?search_query=" + search;
	}
	
	public String shortenUrl(String url) throws MalformedURLException, IOException, GooglException {
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		URL longUrl = new URL(url);
		String postData = "{\"longUrl\": \"" + longUrl.toExternalForm() + "\"}";
		logger.debug("shortenUrl() postData=" + postData);
		
		URL googlUrl = new URL(GOOGL_BASE + "?key=" + apiKey);
		
		HttpURLConnection con = (HttpURLConnection) googlUrl.openConnection();
		con.setRequestMethod("POST");
		con.addRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.connect();
		
		//Send Request
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(postData);
		out.flush();
		out.close();
		
		//Get Response
		GooglResponse response = new GooglResponse(getResponse(con));
		logger.debug("shortUrl=" + response.getShortUrl());
		return response.getShortUrl();
	}
	
	public String expandUrl(String url) throws MalformedURLException, IOException, GooglException {
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		URL shortUrl = new URL(url);
		URL googlUrl = new URL(GOOGL_BASE + "?shortUrl=" + shortUrl.toExternalForm());
		logger.debug("expandUrl() googlUrl=" + googlUrl);
		
		HttpURLConnection con = (HttpURLConnection) googlUrl.openConnection();
		con.setUseCaches(false);
		con.connect();
		
		GooglResponse response = new GooglResponse(getResponse(con));
		logger.debug("longUrl=" + response.getLongUrl());
		con.disconnect();
		return response.getLongUrl();
	}
	
	private String getResponse(HttpURLConnection connection) throws IOException, GooglException {
		boolean gotErrorResponse = false;
		String line;
		InputStream in = null;
		
		if (connection.getResponseCode() == 200) {
			in = connection.getInputStream();
		} else { 
			gotErrorResponse = true;
			in = connection.getErrorStream();
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer response = new StringBuffer();
		while ((line = br.readLine()) != null) {
			response.append(line + "\n");
		}
		br.close();
		
		logger.debug("responseCode=" + connection.getResponseCode());
		connection.disconnect();
		
		if (gotErrorResponse) {
			throw new GooglException(response);
		}
		return response.toString();
	}
}
