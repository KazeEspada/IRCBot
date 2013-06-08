package com.iarekylew00t.encryption;

import org.apache.commons.codec.binary.Base64;

public final class Encoder {
	
	private Encoder() {
		throw new AssertionError();
	}

	public static String encodeBase64(String data) throws Exception { 
		String encoded = Base64.encodeBase64String(data.getBytes());
		return encoded;
	}

	public static String decodeBase64(String encodedData) throws Exception { 
		String decoded = new String(Base64.decodeBase64(encodedData));
		return decoded;
	}

}
