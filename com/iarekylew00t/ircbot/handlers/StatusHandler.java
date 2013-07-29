package com.iarekylew00t.ircbot.handlers;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

import javax.net.SocketFactory;

public class StatusHandler {

	public StatusHandler(){	}
	
	public int getStatusCode(String website) throws Exception {
		URL url = new URL(website);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		int statusCode = conn.getResponseCode();
		conn.disconnect();
		return statusCode;
	}
	
	public boolean getMinecraftStatus(String ip, int port) {
		try {
			Socket socket = SocketFactory.getDefault().createSocket();
			socket.setSoTimeout(5000);
			socket.connect(new InetSocketAddress(ip, port));
			socket.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
