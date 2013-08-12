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
		boolean hasOp = channel.isOp(sender) && !(sender.getNick().equals("Mineb0t") || sender.getNick().equals("Mineb1t"));
		boolean hasVoice = channel.hasVoice(sender) && !(sender.getNick().equals("Mineb0t") || sender.getNick().equals("Mineb1t"));
		
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
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("tellkyle"));
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
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("announce"));
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
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("shout"));
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
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("reboot"));
				return;
				
			/* --- ERROR --- */
			} else if (message.toLowerCase().startsWith("$error")) {
				input = message.substring(6);
				if (StringHelper.isEmpty(input)) {
					if (hasOp) {
						bot.sendMessage(channel, nick + ": " + "" + DataManager.exception);
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("error"));
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
