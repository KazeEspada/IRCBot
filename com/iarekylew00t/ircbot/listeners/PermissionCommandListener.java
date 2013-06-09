package com.iarekylew00t.ircbot.listeners;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.managers.DataManager;

public class PermissionCommandListener extends ListenerAdapter {
	
	@Override
	public void onMessage(MessageEvent event) throws Exception {
		PircBotX bot = event.getBot();
		User sender = event.getUser();
		Channel channel = event.getChannel();
		String message = event.getMessage();
		String nick = sender.getNick();
		String input = "";
		boolean hasOp = channel.isOp(sender);
		boolean hasVoice = channel.hasVoice(sender);
		
		/* === CHECK FOR COMMAND SYMBOL === */
		if(message.startsWith("$")) {	
			
			/* --- TELLKYLE --- */
			if (message.toLowerCase().startsWith("$tellkyle")) {
				input = message.substring(9);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (hasOp || hasVoice) {
						DataManager.emailClient.sendEmail("kyle10468@gmail.com", "NOTICE: Message from " + nick, input);
						bot.sendMessage(channel, Colors.GREEN + "-- Email Sent --");
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				event.respond(StringHelper.getCommand("tellkyle"));
				return;
				
			/* --- ANNOUNCE --- */
			} else if (message.toLowerCase().startsWith("$announce")) {
				input = message.substring(9);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (hasOp) {
						sendToAll(bot, Colors.RED + Colors.BOLD + "ANNOUNCEMENT: " + Colors.OLIVE + input);
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				event.respond(StringHelper.getCommand("announce"));
				return;
				
			/* --- SHOUT --- */
			} else if (message.toLowerCase().startsWith("$shout")) {
				input = message.substring(6);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (hasOp || hasVoice) {
						bot.sendMessage(channel, Colors.RED + Colors.BOLD + input);
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				event.respond(StringHelper.getCommand("shout"));
				return;
				
			/* --- REBOOT --- */
			} else if (message.toLowerCase().startsWith("$reboot")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					if (hasOp) {
						bot.sendMessage(channel, Colors.BOLD + "--- REBOOTING ---");
						Thread.sleep(1000);
						bot.disconnect();
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("reboot"));
				return;
				
			/* --- ERROR --- */
			} else if (message.toLowerCase().startsWith("$error")) {
				input = message.substring(6);
				if (StringHelper.isEmpty(input)) {
					if (hasOp) {
						event.respond("" + DataManager.exception);
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("error"));
				return;
			}
			return;
		}
		return;
	}
	
	private void sendToAll(PircBotX bot, String message) {
		Channel[] channels = bot.getChannels().toArray(new Channel[0]);
		for (int i = 0; i < channels.length; i++) {
			bot.sendMessage(channels[i], message);
		}
	}
}
