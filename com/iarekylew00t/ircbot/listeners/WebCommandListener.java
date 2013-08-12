package com.iarekylew00t.ircbot.listeners;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.chatbot.ChatBot;
import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.ircbot.handlers.Definition;
import com.iarekylew00t.ircbot.handlers.DefinitionHandler;
import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.ircbot.handlers.RssHandler;
import com.iarekylew00t.ircbot.handlers.StatusHandler;
import com.iarekylew00t.ircbot.handlers.Weather;
import com.iarekylew00t.ircbot.handlers.WeatherHandler;
import com.iarekylew00t.managers.DataManager;

public class WebCommandListener extends ListenerAdapter {
	private String WIKI_BASE = "http://en.wikipedia.org/wiki/";
	private DefinitionHandler dictionary = new DefinitionHandler(); 
	private RssHandler rss = new RssHandler("http://skaianet.net/rss", 5);
	private WeatherHandler forecast = new WeatherHandler();
	private ChatBot chatbot = new ChatBot();
	private StatusHandler status = new StatusHandler();
	private LogHandler logger = DataManager.logHandler;
	
	@Override
	public void onMessage(MessageEvent event) throws Exception {
		PircBotX bot = event.getBot();
		User sender = event.getUser();
		Channel channel = event.getChannel();
		String message;
		if (sender.getNick().equals("Mineb0t") || sender.getNick().equals("Mineb1t")) {
			message = event.getMessage()
					.replaceAll(Colors.BLACK, "")
					.replaceAll(Colors.BLUE, "")
					.replaceAll(Colors.BOLD, "")
					.replaceAll(Colors.BROWN, "")
					.replaceAll(Colors.CYAN, "")
					.replaceAll(Colors.DARK_BLUE, "")
					.replaceAll(Colors.DARK_GRAY, "")
					.replaceAll(Colors.DARK_GREEN, "")
					.replaceAll(Colors.GREEN, "")
					.replaceAll(Colors.LIGHT_GRAY, "")
					.replaceAll(Colors.MAGENTA, "")
					.replaceAll(Colors.NORMAL, "")
					.replaceAll(Colors.OLIVE, "")
					.replaceAll(Colors.PURPLE, "")
					.replaceAll(Colors.RED, "")
					.replaceAll(Colors.REVERSE, "")
					.replaceAll(Colors.TEAL, "")
					.replaceAll(Colors.UNDERLINE, "")
					.replaceAll(Colors.WHITE, "")
					.replaceAll(Colors.YELLOW, "")
					.replaceFirst("<+.*?> ", "");
		} else {
			message = event.getMessage();
		}
		String nick;
		if (sender.getNick().equals("Mineb0t") || sender.getNick().equals("Mineb1t")) {
			nick = event.getMessage()
					.replaceAll(Colors.BLACK, "")
					.replaceAll(Colors.BLUE, "")
					.replaceAll(Colors.BOLD, "")
					.replaceAll(Colors.BROWN, "")
					.replaceAll(Colors.CYAN, "")
					.replaceAll(Colors.DARK_BLUE, "")
					.replaceAll(Colors.DARK_GRAY, "")
					.replaceAll(Colors.DARK_GREEN, "")
					.replaceAll(Colors.GREEN, "")
					.replaceAll(Colors.LIGHT_GRAY, "")
					.replaceAll(Colors.MAGENTA, "")
					.replaceAll(Colors.NORMAL, "")
					.replaceAll(Colors.OLIVE, "")
					.replaceAll(Colors.PURPLE, "")
					.replaceAll(Colors.RED, "")
					.replaceAll(Colors.REVERSE, "")
					.replaceAll(Colors.TEAL, "")
					.replaceAll(Colors.UNDERLINE, "")
					.replaceAll(Colors.WHITE, "")
					.replaceAll(Colors.YELLOW, "")
					.replaceFirst("<", "").replaceFirst(">.*", "");
		} else {
			nick = sender.getNick();
		}
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
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("define"));
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
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("udefine"));
				return;
				
			/* --- RSS --- */
			} else if (message.toLowerCase().startsWith("$rss")) {
				input = message.substring(4);
				if (StringHelper.isEmpty(input)) {
					try {
						bot.sendMessage(channel, Colors.OLIVE + Colors.BOLD + rss.getLatest());
						return;
					} catch (Exception e) { 
						bot.sendMessage(channel, DataManager.ERROR + "Could not parse data from XML");
						logger.error("COULD NOT PARSE DATA FROM XML", e);
						return;
					}
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("rss"));
				return;
				
			/* --- STATUS --- */
			} else if (message.toLowerCase().startsWith("$status")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					String statusMessage = "";
					try {
						if (status.getStatusCode("http://skaianet.net") == 200) {
							statusMessage += "Blog: " + Colors.GREEN + Colors.BOLD + "ONLINE" + Colors.NORMAL + " | ";
						} else {
							statusMessage += "Blog: " + Colors.RED + Colors.BOLD + "OFFLINE" + Colors.NORMAL + " | ";
						}
						if (status.getStatusCode("http://skaianet.net/radio") == 200 && status.getStatusCode("http://mixlr.com/skaianetradio/") == 200) {
							statusMessage += "Radio: " + Colors.GREEN + Colors.BOLD + "ONLINE" + Colors.NORMAL + " | ";
						} else {
							statusMessage += "Radio: " + Colors.RED + Colors.BOLD + "OFFLINE" + Colors.NORMAL + " | ";
						}
						if (status.getRawStatus("irc.esper.net", 6667) == true) {
							statusMessage += "IRC: " + Colors.GREEN + Colors.BOLD + "ONLINE" + Colors.NORMAL + " | ";
						} else {
							statusMessage += "IRC: " + Colors.RED + Colors.BOLD + "OFFLINE" + Colors.NORMAL + " | ";
						}
						if (status.getRawStatus("mc.skaianet.net", 25565) == true) {
							statusMessage += "MC: " + Colors.GREEN + Colors.BOLD + "ONLINE" + Colors.NORMAL;
						} else {
							statusMessage += "MC: " + Colors.RED + Colors.BOLD + "OFFLINE" + Colors.NORMAL;
						}
						bot.sendMessage(channel, statusMessage);
						return;
					} catch (Exception e) { 
						bot.sendMessage(channel, DataManager.ERROR + "Could check status");
						logger.error("COULD NOT PARSE DATA FROM XML", e);
						return;
					}
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("status"));
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
					if (channel.getName().equals("#skaianet_chat3")) {
						try {
							bot.sendMessage(channel, chatbot.think(input));
							return;
						} catch (Exception e) {
							bot.sendMessage(channel, DataManager.ERROR + "Could not think of a response");
							logger.error("COULD NOT THINK OF RESPONSE", e);
							return;
						}
					}
					bot.sendMessage(channel, "i can 0nly talk pe0ple in channel 3");
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("talk"));
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
						bot.sendMessage(channel, DataManager.ERROR + "Could not parse data from XML");
						logger.error("COULD NOT PARSE DATA FROM XML", e);
						return;
					}
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("weather"));
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
						bot.sendMessage(channel, DataManager.ERROR + "Could not parse data from XML");
						logger.error("COULD NOT PARSE DATA FROM XML", e);
						return;
					}
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("weather"));
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
					bot.sendMessage(channel, nick + ": " + WIKI_BASE + input);
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("wiki"));
				return;
			}
			return;
		}
		return;
	}
}
