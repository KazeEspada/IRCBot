package com.iarekylew00t.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ByteGenerator {

	public ByteGenerator() {}
	
    public static byte[] genRandomByte(int size) {
        SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
	
	public static String toHex(byte[] digest) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			String hex = Integer.toHexString(0xFF & digest[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
	
}
