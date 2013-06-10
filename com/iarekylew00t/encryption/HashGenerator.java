package com.iarekylew00t.encryption;

import java.security.MessageDigest;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public final class HashGenerator {
	private static LogHandler logger = DataManager.logHandler;

	private HashGenerator() {
		throw new AssertionError();
	}
	
	public static String hashMD2(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("MD2");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		String hashVal = ByteGenerator.toHex(hashValue);
		logger.debug("data=" + data);
		logger.debug("hashVal=" + hashVal);
		return hashVal;
	}
	
	public static String hashMD5(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		String hashVal = ByteGenerator.toHex(hashValue);
		logger.debug("data=" + data);
		logger.debug("hashVal=" + hashVal);
		return hashVal;
	}
	
	public static String hashSHA1(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		String hashVal = ByteGenerator.toHex(hashValue);
		logger.debug("data=" + data);
		logger.debug("hashVal=" + hashVal);
		return hashVal;
	}
	
	public static String hashSHA256(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		String hashVal = ByteGenerator.toHex(hashValue);
		logger.debug("data=" + data);
		logger.debug("hashVal=" + hashVal);
		return hashVal;
	}
	
	public static String hashSHA384(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-384");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		String hashVal = ByteGenerator.toHex(hashValue);
		logger.debug("data=" + data);
		logger.debug("hashVal=" + hashVal);
		return hashVal;
	}
	
	public static String hashSHA512(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		String hashVal = ByteGenerator.toHex(hashValue);
		logger.debug("data=" + data);
		logger.debug("hashVal=" + hashVal);
		return hashVal;
	}
}
