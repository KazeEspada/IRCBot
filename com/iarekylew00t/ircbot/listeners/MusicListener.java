package com.iarekylew00t.ircbot.listeners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.email.EmailClient;
import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.ircbot.handlers.MusicHandler;
import com.iarekylew00t.managers.DataManager;

public class MusicListener extends ListenerAdapter {
	private MusicHandler winamp = new MusicHandler();
	private LogHandler logger = DataManager.logHandler;
	private EmailClient email = DataManager.emailClient;
	
	@Override
	public void onMessage(MessageEvent event) {
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
		boolean hasOp = channel.isOp(sender);
		boolean hasVoice = channel.hasVoice(sender);
		
		/* === CHECK FOR COMMAND SYMBOL === */
		if (message.startsWith("$")) {
			
			/* --- PREVIOUS SONG --- */
			if (message.toLowerCase().startsWith("$prevsong")) {
				input = message.substring(9);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					int number = 0;
					try {
						number = Integer.parseInt(StringHelper.setString(input));
					} catch (Exception e) {
						bot.sendMessage(channel, DataManager.ERROR + "I cannot handle numbers like that");
						return;
					}
					if (number > 5) {
						bot.sendMessage(channel, nick + ": i can 0nly sh0w up t0 5 previ0us s0ngs");
						return;
					} else if (number <= 0) {
						bot.sendMessage(channel, nick + ": please enter a number fr0m 1-5");
						return;
					}
					String songs = winamp.getPrevSong(0);
					for (int i = 1; i < number-1; i++) {
						songs += ", " + winamp.getPrevSong(i);
					}
					bot.sendMessage(channel, "the last " + number + " s0ngs were: " + Colors.BOLD + songs);
					return;
				}
				bot.sendMessage(channel, "the previ0us s0ng was: " + Colors.BOLD + winamp.getPrevSong());
				return;
			} else if (message.toLowerCase().startsWith("$prev")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					int number = 0;
					try {
						number = Integer.parseInt(StringHelper.setString(input));
					} catch (Exception e) {
						bot.sendMessage(channel, DataManager.ERROR + "I cannot handle numbers like that");
						return;
					}
					if (number > 5) {
						bot.sendMessage(channel, nick + ": i can 0nly sh0w up t0 5 previ0us s0ngs");
						return;
					} else if (number <= 0) {
						bot.sendMessage(channel, nick + ": please enter a number fr0m 1-5");
						return;
					}
					String songs = winamp.getPrevSong(0);
					if (number > 1) {
						for (int i = 1; i <= number-1; i++) {
							songs += ", " + winamp.getPrevSong(i);
						}
						bot.sendMessage(channel, "the last " + number + " s0ngs were: " + Colors.BOLD + songs);
						return;
					}
					bot.sendMessage(channel, "the last s0ng was: " + Colors.BOLD + songs);
					return;
				}
				bot.sendMessage(channel, "the previ0us s0ng was: " + Colors.BOLD + winamp.getPrevSong());
				return;
				
				
			/* --- SONGLIST --- */
			} else if (message.toLowerCase().startsWith("$songlist")) {
				input = message.substring(9);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, nick + ": " + "http://skaianet.net/songlist");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("songlist"));
				return;
				
			/* --- CURRENT SONG --- */
			} else if (message.toLowerCase().startsWith("$cursong")) {
				input = message.substring(8);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, "the current s0ng is: " + Colors.BOLD + winamp.getCurSong());
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("song"));
				return;
			} else if (message.toLowerCase().startsWith("$song")) {
				input = message.substring(5);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, "the current s0ng is: " + Colors.BOLD + winamp.getCurSong());
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("song"));
				return;
				
				
			/* --- RESTART WINAMP --- */
			} else if (message.toLowerCase().startsWith("$restart")) {
				if (hasOp || hasVoice) {
					input = message.substring(8);
					if (StringHelper.isEmpty(input)) {
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
						Date date = new Date();
				    	String curTime = dateFormat.format(date);
				        try {
				        	winamp.restartWinamp(channel, sender);
							return;
				        } catch (Exception e) {
				        	logger.error("COULD NOT RESTART WINAMP", e);
				        	bot.sendMessage(channel, DataManager.ERROR + "Failed to restart Winamp - Notifying IAreKyleW00t...");
							bot.sendMessage(channel, "Please notify IAreKyleW00t manually to make sure he knows: http://iarekylew00t.me/ask");
				        	email.sendEmail("kyle10468@gmail.com", "WARNING: Winamp Failed to Restart", "Winamp FAILED to restart @ " + curTime);
							return;
				        }
					}
					if (!input.startsWith(" ")) {
						return;
					}
					bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("restart"));
					return;
				}
				bot.sendMessage(channel, nick + ": " + "y0u d0nt have permissi0n t0 use that c0mmand");
				return;
			}
			return;
		}
		return;
	}
}
