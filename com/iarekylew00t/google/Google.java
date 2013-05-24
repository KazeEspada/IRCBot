package com.iarekylew00t.google;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Google {
	private static final String VER = "0.0.1.5";
	private static final String GOOGL_BASE = "https://www.googleapis.com/urlshortener/v1/url";
	private String apiKey;

	public Google(String api) {
		System.out.println("----- CHECKING GOOGLE APIKEY -----");
        if (api.isEmpty()) {
    		System.out.println("----- ERROR: PLEASE USE A VALID APIKEY -----");
            throw new IllegalArgumentException("APIKey must be specified, see the Google URL Shortener API docs: http://goo.gl/2rfGn");
    }
		System.out.println("----- GOOGLE APIKEY VALID -----");
        apiKey = api;
	}
	
	public String shortenUrl(String url) throws MalformedURLException, IOException, GooglException {
		System.out.println("----- SHORTENING URL -----");
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		URL longUrl = new URL(url);
		String postData = "{\"longUrl\": \"" + longUrl.toExternalForm() + "\"}";
		System.out.println("shortenUrl() postData=" + postData);
		
		URL googlUrl = new URL(GOOGL_BASE + "?key=" + apiKey);
		System.out.println("shortenUrl() googlUrl=" + googlUrl);
		
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
		System.out.println("shortUrl=" + response.getShortUrl());

		System.out.println("----- URL SUCCESSFULLY SHORTENED -----");
		return response.getShortUrl();
	}
	
	public String expandUrl(String url) throws MalformedURLException, IOException, GooglException {
		System.out.println("----- EXPANDING URL -----");
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		URL shortUrl = new URL(url);
		URL googlUrl = new URL(GOOGL_BASE + "?shortUrl=" + shortUrl.toExternalForm());
		System.out.println("expandUrl() googlUrl=" + googlUrl);
		
		HttpURLConnection con = (HttpURLConnection) googlUrl.openConnection();
		con.setUseCaches(false);
		con.connect();
		
		GooglResponse response = new GooglResponse(getResponse(con));
		con.disconnect();

		System.out.println("-----  URL SUCCESSFULLY EXPANDED -----");
		return response.getLongUrl();
	}
	
	private String getResponse(HttpURLConnection connection) throws IOException, GooglException {
		System.out.println("----- FETCHING RESPONSE -----");
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
		
		System.out.println("responseCode=" + connection.getResponseCode());
		System.out.println("response=" + response);
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
