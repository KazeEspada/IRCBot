package com.iarekylew00t.chatbot;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class ChatBot {
	private ChatterBotFactory factory;
	private ChatterBotSession session;
	private ChatterBot bot;
	private LogHandler logger = DataManager.logHandler;
	
	public ChatBot() {
		factory  = new ChatterBotFactory();
		try {
			bot = factory.create(ChatterBotType.CLEVERBOT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session = bot.createSession();
	}
	
	public String think(String input) throws Exception {
		String response;
		response = session.think(input);
		response = response.toLowerCase().replace("o", "0").replace("O", "0");
		response = response.replace("cleverb0t", "Aradiabot");
		logger.debug("input=" + input);
		logger.debug("response=" + response);
		return response;
	}
}
