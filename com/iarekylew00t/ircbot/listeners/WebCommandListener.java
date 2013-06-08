package com.iarekylew00t.ircbot.listeners;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.chatbot.ChatBot;
import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.ircbot.handlers.Definition;
import com.iarekylew00t.ircbot.handlers.DefinitionHandler;
import com.iarekylew00t.ircbot.handlers.Weather;
import com.iarekylew00t.ircbot.handlers.WeatherHandler;
import com.iarekylew00t.managers.DataManager;

public class WebCommandListener extends ListenerAdapter {
	private String WIKI_BASE = "http://en.wikipedia.org/wiki/";
	private DefinitionHandler dictionary = new DefinitionHandler(); 
	private WeatherHandler forecast = new WeatherHandler();
	private ChatBot chatbot = new ChatBot();
	
	@Override
	public void onMessage(MessageEvent event) throws Exception {
		PircBotX bot = event.getBot();
		Channel channel = event.getChannel();
		String message = event.getMessage();
		String input = "";
		
		/* === CHECK FOR COMMAND SYMBOL === */
		if(message.startsWith("$")) {	
			
			/* --- DEFINE --- */
			if (message.toLowerCase().startsWith("$define")) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					Definition word = dictionary.getDefinition(input);
					if (word.getDefinition().length() > 300) {
						String definition = word.getDefinition();
						bot.sendMessage(channel, Colors.BOLD + word.getWord() + Colors.NORMAL + ": " + StringHelper.trimString(definition, definition.length()-300) + "... Read More: " + DataManager.google.shortenUrl(word.getUrl()));
						return;
					}
					bot.sendMessage(channel, Colors.BOLD + word.getWord() + Colors.NORMAL + ": " + word.getDefinition());
					return;
				}
				event.respond(StringHelper.getCommand("define"));
				return;
				
			/* --- URBAN DEFINE --- */
			} else if (message.toLowerCase().startsWith("$udefine")) {
				input = message.substring(8);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					Definition word = dictionary.getUrbanDefinition(input);
					if (word.getDefinition().length() > 300) {
						String definition = word.getDefinition();
						bot.sendMessage(channel, Colors.BOLD + word.getWord() + Colors.NORMAL + ": " + StringHelper.trimString(definition, definition.length()-300) + "... Read More: " + DataManager.google.shortenUrl(word.getUrl()));
						return;
					}
					bot.sendMessage(channel, Colors.BOLD + word.getWord() + Colors.NORMAL + ": " + word.getDefinition());
					return;
				}
				event.respond(StringHelper.getCommand("udefine"));
				return;
				
			/* --- TALK --- */
			} else if (message.toLowerCase().startsWith("$talk")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					/* === CHECK IF CHANNEL 3 === */
					if (channel.getName().equals("#hs_radio3")) {
						try {
							bot.sendMessage(channel, chatbot.think(input));
							return;
						} catch (Exception e) {
							bot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not think of a response");
							e.printStackTrace();
							DataManager.exception = e;
							return;
						}
					}
					bot.sendMessage(channel, "i can 0nly talk pe0ple in channel 3");
					return;
				}
				event.respond(StringHelper.getCommand("talk"));
				return;
				
			/* --- WEATHER --- */
			} else if (message.toLowerCase().startsWith("$weather")) {
				input = message.substring(8);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					try {
						Weather weather = forecast.getForecast(input);
						bot.sendMessage(channel, Colors.BOLD + weather.getLocation() + Colors.NORMAL + " - it is " +
													Colors.BOLD + weather.getTempUnits() + Colors.NORMAL + " 0utside " + 
													"(feels like " + Colors.BOLD + weather.getFeelsTempUnits() + Colors.NORMAL + 
													") and the c0ndit0ns are: " + Colors.BOLD + weather.getCondition());
						return;
					} catch (Exception e) {
						bot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not parse data from XML");
						return;
					}
				}
				event.respond(StringHelper.getCommand("weather"));
				return;
			} else if (message.toLowerCase().startsWith("$we") && !(message.toLowerCase().startsWith("$weather"))) {
				input = message.substring(3);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					try {
						Weather weather = forecast.getForecast(input);
						bot.sendMessage(channel, Colors.BOLD + weather.getLocation() + Colors.NORMAL + " - it is " +
													Colors.BOLD + weather.getTempUnits() + " 0utside " + Colors.NORMAL + 
													"(feels like " + Colors.BOLD + weather.getFeelsTempUnits() + Colors.NORMAL + 
													") and the c0ndit0ns are: " + Colors.BOLD + weather.getCondition());
						return;
					} catch (Exception e) {
						bot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not parse data from XML");
						return;
					}
				}
				event.respond(StringHelper.getCommand("weather"));
				return;
				
			/* --- WIKI --- */
			} else if (message.toLowerCase().startsWith("$wiki")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					input = input.replace(" ", "_");
					event.respond(WIKI_BASE + input);
					return;
				}
				event.respond(StringHelper.getCommand("wiki"));
				return;
			}
			return;
		}
		return;
	}
}
