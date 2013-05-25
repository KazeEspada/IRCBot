package com.iarekylew00t.google;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.iarekylew00t.ircbot.LogHandler;

public class Google {
	private static final String VER = "0.0.1.5";
	private static final String GOOGL_BASE = "https://www.googleapis.com/urlshortener/v1/url";
	private String apiKey;
	private LogHandler logger = new LogHandler();
	
	public Google(String api) {
		logger.notice("CHECKING GOOGLE APIKEY");
        if (api.isEmpty()) {
        	logger.error("ERROR: PLEASE USE A VALID APIKEY");
            throw new IllegalArgumentException("APIKey must be specified, see the Google URL Shortener API docs: http://goo.gl/2rfGn");
    }
        logger.notice("GOOGLE APIKEY VALID");
        apiKey = api;
	}
	
	public String shortenUrl(String url) throws MalformedURLException, IOException, GooglException {
		logger.notice("SHORTENING URL");
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		URL longUrl = new URL(url);
		String postData = "{\"longUrl\": \"" + longUrl.toExternalForm() + "\"}";
		logger.debug("shortenUrl() postData=" + postData);
		
		URL googlUrl = new URL(GOOGL_BASE + "?key=" + apiKey);
		logger.debug("shortenUrl() googlUrl=" + googlUrl);
		
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

		logger.notice("URL SUCCESSFULLY SHORTENED");
		return response.getShortUrl();
	}
	
	public String expandUrl(String url) throws MalformedURLException, IOException, GooglException {
		logger.notice("EXPANDING URL");
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
		con.disconnect();

		logger.notice(" URL SUCCESSFULLY EXPANDED");
		return response.getLongUrl();
	}
	
	private String getResponse(HttpURLConnection connection) throws IOException, GooglException {
		logger.notice("FETCHING RESPONSE");
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
		logger.debug("response=" + response);
		connection.disconnect();
		
		if (gotErrorResponse) {
			throw new GooglException(response);
		}
		return response.toString();
	}
	
	public String getVersion() {
		return VER;
	}
}