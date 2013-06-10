package com.iarekylew00t.encryption;

import org.apache.commons.codec.binary.Base64;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public final class Encoder {
	private static LogHandler logger = DataManager.logHandler;
	
	private Encoder() {
		throw new AssertionError();
	}

	public static String encodeBase64(String data) throws Exception {
		String encoded = Base64.encodeBase64String(data.getBytes());
		logger.debug("data=" + data);
		logger.debug("encoded=" + encoded);
		return encoded;
	}

	public static String decodeBase64(String encodedData) throws Exception {
		String decoded = new String(Base64.decodeBase64(encodedData));
		logger.debug("encodedData=" + encodedData);
		logger.debug("decoded=" + decoded);
		return decoded;
	}

}
