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
		String message = event.getMessage();
		String input = "";
		boolean hasOp = channel.isOp(sender);
		boolean hasVoice = channel.hasVoice(sender);
		
		/* === CHECK FOR COMMAND SYMBOL === */
		if (message.startsWith("$")) {
			
			/* --- PREVIOUS SONG --- */
			if (message.toLowerCase().startsWith("$prevsong")) {
				input = message.substring(9);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, "the previ0us s0ng was: " + Colors.BOLD + winamp.getPrevSong());
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("prevsong"));
				return;
			} else if (message.toLowerCase().startsWith("$prev")) {
				input = message.substring(5);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, "the previ0us s0ng was: " + Colors.BOLD + winamp.getPrevSong());
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("prevsong"));
				return;
				
				
			/* --- SONGLIST --- */
			} else if (message.toLowerCase().startsWith("$songlist")) {
				input = message.substring(9);
				if (StringHelper.isEmpty(input)) {
					event.respond("http://iarekylew00t.tumblr.com/song-list");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("songlist"));
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
				event.respond(StringHelper.getCommand("song"));
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
				event.respond(StringHelper.getCommand("song"));
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
							bot.sendMessage(channel, "Please notify IAreKyleW00t manually to make sure he knows: http://iarekylew00t.tumblr.com/ask");
				        	email.sendEmail("kyle10468@gmail.com", "WARNING: Winamp Failed to Restart", "Winamp FAILED to restart @ " + curTime);
							return;
				        }
					}
					if (!input.startsWith(" ")) {
						return;
					}
					event.respond(StringHelper.getCommand("restart"));
					return;
				}
				event.respond("y0u d0nt have permissi0n t0 use that c0mmand");
				return;
			}
			return;
		}
		return;
	}
}
