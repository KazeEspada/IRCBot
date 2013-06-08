package com.iarekylew00t.ircbot;

import org.pircbotx.PircBotX;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class IRCBot extends PircBotX {
	LogHandler logger = DataManager.logHandler;

	@Override
	public void log(String line) {
		if (line.startsWith(">>>") && !(line.startsWith(">>>WHO") || line.startsWith(">>>MODE"))) {
			if (!DataManager.nickPassword.isEmpty()) {
				if (line.contains(DataManager.nickPassword) || line.contains("IDENTIFY")) {
					line = line.replace(DataManager.nickPassword, "*******");
				}
			}
			logger.info(">>> " + line.substring(3));
		}
	}
}
