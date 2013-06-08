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
	
}
