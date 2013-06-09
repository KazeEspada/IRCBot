package com.iarekylew00t.encryption;

import java.security.MessageDigest;

public final class HashGenerator {

	private HashGenerator() {
		throw new AssertionError();
	}
	
	public static String hashMD2(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("MD2");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		return ByteGenerator.toHex(hashValue);
	}
	
	public static String hashMD5(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		return ByteGenerator.toHex(hashValue);
	}
	
	public static String hashSHA1(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		return ByteGenerator.toHex(hashValue);
	}
	
	public static String hashSHA256(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		return ByteGenerator.toHex(hashValue);
	}
	
	public static String hashSHA384(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-384");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		return ByteGenerator.toHex(hashValue);
	}
	
	public static String hashSHA512(String data) throws Exception { 
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.update(data.getBytes());
		byte[] bytes = digest.digest();
		byte[] hashValue = digest.digest(bytes);
		return ByteGenerator.toHex(hashValue);
	}
}
