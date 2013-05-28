package com.iarekylew00t.chatbot;

import com.google.code.chatterbotapi.*;
import com.iarekylew00t.ircbot.LogHandler;

public class ChatBot {
	private ChatterBotFactory factory;
	private ChatterBotSession session;
	private ChatterBot bot;
	private String response = "";
	private LogHandler logger = new LogHandler();

	public ChatBot() {
		logger.notice("*** SETTING UP CHATBOT ***");
		factory  = new ChatterBotFactory();
		try {
			bot = factory.create(ChatterBotType.CLEVERBOT);
		} catch (Exception e) {
			logger.error("FAILED TO CREATE CHATTERBOT");
			logger.error(e);
		}
		session = bot.createSession();
		logger.notice("*** CHATBOT SETUP SUCCESSFULLY ***");
	}
	
	public String think(String message) {
		try {
			response = session.think(message).replace("Cleverbot", "Aradiabot");
			logger.debug("bot.think()=" + response);
			response = response.toLowerCase().replace("o", "0").replace("O", "0");
			return response.replace("aradiab0t", "Aradiabot");
		} catch (Exception e) {
			logger.error("COULD NOT THINK OF RESPONSE");
			logger.error(e);
			return "Error: Could not think of response";
		}
	}

}
