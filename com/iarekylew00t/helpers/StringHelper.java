package com.iarekylew00t.helpers;

import com.iarekylew00t.managers.DataManager;

public final class StringHelper {
	
	private StringHelper() {
		throw new AssertionError();
	}
	
	public static String setString(String str) {
		return str.substring(1).trim();
	}
	
	public static String getCommand(String command) {
		return DataManager.commandList.get(command);
	}
	
    public static String trimString(String s, int num) {
        if (s == null || s.length() == 0) {
            return s;
        }
        s = s.substring(0, s.length()-num);
        return s;
    }
    
	public static boolean isEmpty(String input) {
		if(input.replaceAll("\\s", "").isEmpty()) {
			return true;
		}
		return false;
	}
}
