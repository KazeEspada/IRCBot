package com.iarekylew00t.ircbot.listeners;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.google.Google;
import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class GoogleListener extends ListenerAdapter {
	private Google google = DataManager.google;
	private LogHandler logger = DataManager.logHandler;
	
	@Override
	public void onMessage(MessageEvent event) {
		PircBotX bot = event.getBot();
		Channel channel = event.getChannel();
		String message = event.getMessage();
		String input = "";

		/* === CHECK FOR COMMAND SYMBOL === */
		if(message.startsWith("$")) {	
			
			/* --- EXPAND URL --- */
			if (message.toLowerCase().startsWith("$expand")) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.startsWith("goo.gl/") || input.startsWith("http://goo.gl/")) {
						try {
							event.respond(google.expandUrl(input));
							return;
						} catch (Exception e) {
							logger.error("COULD NOT EXPAND URL", e);
							bot.sendMessage(channel, DataManager.ERROR + "Could not expand URL");
							return;
						}
					}
				}
				event.respond(StringHelper.getCommand("expand"));
				return;
				
			/* --- SHORTEN URL --- */
			} else if (message.toLowerCase().startsWith("$shorten")) {
				input = message.substring(8);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					try {
						event.respond(google.shortenUrl(input));
						return;
					} catch (Exception e) {
						logger.error("COULD NOT SHORTEN URL", e);
						bot.sendMessage(channel, DataManager.ERROR + "Could not shorten URL");
						return;
					}
				}
				event.respond(StringHelper.getCommand("shorten"));
				return;
				
			/* --- GOOGLE SEARCH --- */
			//TODO IMPROVE GOOGLE SEARCH
			} else if (message.toLowerCase().startsWith("$google")) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					event.respond(google.googleSearch(input));
					return;
				}
				event.respond(StringHelper.getCommand("google"));
				return;
			} else if (message.toLowerCase().startsWith("$g") && !(message.toLowerCase().startsWith("$gearup") || message.toLowerCase().startsWith("$github") || message.toLowerCase().startsWith("$google"))) {
				input = message.substring(2);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					event.respond(google.googleSearch(input));
					return;
				}
				event.respond(StringHelper.getCommand("google"));
				return;
				
			/* --- YOUTUBE SEARCH --- */
			//TODO IMPROVE YOUTUBE SEARCH
			} else if (message.toLowerCase().startsWith("$yt") && !message.toLowerCase().startsWith("$youtube")) {
				input = message.substring(3);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					event.respond(google.youtubeSearch(input));
					return;
				}
				event.respond(StringHelper.getCommand("youtube"));
				return;
			} else if (message.toLowerCase().startsWith("$youtube")) {
				input = message.substring(8);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					event.respond(google.youtubeSearch(input));
					return;
				}
				event.respond(StringHelper.getCommand("youtube"));
				return;
			}
			return;
		}
		return;
	}
}
