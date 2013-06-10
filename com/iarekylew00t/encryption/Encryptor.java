package com.iarekylew00t.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public final class Encryptor {
	private static final byte[] salt = DataManager.salt.substring(0, 16).getBytes();
	private static final byte[] IV = "7@>X,C=<;'ZS9$!k".getBytes();
	private static final byte[] DESsalt = DataManager.salt.substring(0, 8).getBytes();
	private static final byte[] DESIV = "7@>X,C=$".getBytes();
	private static final byte[] DES3salt = DataManager.salt.substring(0, 24).getBytes();
	private static LogHandler logger = DataManager.logHandler;

	private Encryptor() {
		throw new AssertionError();
	}
	
	public static String encryptAES(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(salt, "AES");
		IvParameterSpec IVSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, key, IVSpec);
		byte[] encVal = cipher.doFinal(data.getBytes());
		String encryptedVal = Base64.encodeBase64String(encVal);
		logger.debug("data=" + data);
		logger.debug("encryptedVal=" + encryptedVal);
		return encryptedVal;
	}
	
	public static String decryptAES(String encryptedData) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(salt, "AES");
		IvParameterSpec IVSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, key, IVSpec);
		byte[] decVal = cipher.doFinal(Base64.decodeBase64(encryptedData));
		String decryptedVal = new String(decVal);
		logger.debug("encryptedData=" + encryptedData);
		logger.debug("decryptedData=" + decryptedVal);
		return decryptedVal;
	}
	
	public static String encryptDES(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(DESsalt, "DES");
		IvParameterSpec IVSpec = new IvParameterSpec(DESIV);
		cipher.init(Cipher.ENCRYPT_MODE, key, IVSpec);
		byte[] encVal = cipher.doFinal(data.getBytes());
		String encryptedVal = Base64.encodeBase64String(encVal);
		logger.debug("data=" + data);
		logger.debug("encryptedVal=" + encryptedVal);
		return encryptedVal;
	}
	
	public static String decryptDES(String encryptedData) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(DESsalt, "DES");
		IvParameterSpec IVSpec = new IvParameterSpec(DESIV);
		cipher.init(Cipher.DECRYPT_MODE, key, IVSpec);
		byte[] decVal = cipher.doFinal(Base64.decodeBase64(encryptedData));
		String decryptedVal = new String(decVal);
		logger.debug("encryptedData=" + encryptedData);
		logger.debug("decryptedData=" + decryptedVal);
		return decryptedVal;
	}
	
	public static String encrypt3DES(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(DES3salt, "DESede");
		IvParameterSpec IVSpec = new IvParameterSpec(DESIV);
		cipher.init(Cipher.ENCRYPT_MODE, key, IVSpec);
		byte[] encVal = cipher.doFinal(data.getBytes());
		String encryptedVal = Base64.encodeBase64String(encVal);
		logger.debug("data=" + data);
		logger.debug("encryptedVal=" + encryptedVal);
		return encryptedVal;
	}
	
	public static String decrypt3DES(String encryptedData) throws Exception {
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(DES3salt, "DESede");
		IvParameterSpec IVSpec = new IvParameterSpec(DESIV);
		cipher.init(Cipher.DECRYPT_MODE, key, IVSpec);
		byte[] decVal = cipher.doFinal(Base64.decodeBase64(encryptedData));
		String decryptedVal = new String(decVal);
		logger.debug("encryptedData=" + encryptedData);
		logger.debug("decryptedData=" + decryptedVal);
		return decryptedVal;
	}
	
	public static String encryptRC2(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("RC2");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(salt, "RC2");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = cipher.doFinal(data.getBytes());
		String encryptedVal = Base64.encodeBase64String(encVal);
		logger.debug("data=" + data);
		logger.debug("encryptedVal=" + encryptedVal);
		return encryptedVal;
	}
	
	public static String decryptRC2(String encryptedData) throws Exception {
		Cipher cipher = Cipher.getInstance("RC2");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(salt, "RC2");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decVal = cipher.doFinal(Base64.decodeBase64(encryptedData));
		String decryptedVal = new String(decVal);
		logger.debug("encryptedData=" + encryptedData);
		logger.debug("decryptedData=" + decryptedVal);
		return decryptedVal;
	}
	
	public static String encryptBlowfish(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("Blowfish");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(salt, "Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = cipher.doFinal(data.getBytes());
		String encryptedVal = Base64.encodeBase64String(encVal);
		logger.debug("data=" + data);
		logger.debug("encryptedVal=" + encryptedVal);
		return encryptedVal;
	}
	
	public static String decryptBlowfish(String encryptedData) throws Exception {
		Cipher cipher = Cipher.getInstance("Blowfish");
		logger.debug("salt=" + ByteGenerator.toHex(salt));
		final SecretKeySpec key = new SecretKeySpec(salt, "Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decVal = cipher.doFinal(Base64.decodeBase64(encryptedData));
		String decryptedVal = new String(decVal);
		logger.debug("encryptedData=" + encryptedData);
		logger.debug("decryptedData=" + decryptedVal);
		return decryptedVal;
	}
}
