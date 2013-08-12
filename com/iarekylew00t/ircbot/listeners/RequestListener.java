package com.iarekylew00t.ircbot.listeners;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.ircbot.handlers.RequestHandler;

public class RequestListener extends ListenerAdapter {
	private RequestHandler requests = new RequestHandler();
	
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
		boolean hasOp = channel.isOp(sender) && !(sender.getNick().equals("Mineb0t") || sender.getNick().equals("Mineb1t"));

		/* === CHECK FOR COMMAND SYMBOL === */
		if(message.startsWith("$")) {	
			
			/* --- REQ --- */
			if (message.toLowerCase().startsWith("$req")) {
				input = message.substring(4);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					//Check for -on argument
					if (input.toLowerCase().startsWith("-on")) {
						if (hasOp) {
						//Check if a time is specified
						if (input.contains(" ")) {
							String[] args = input.split(" ");
							int time = 1;
								if (!args[0].equals("-on")) {
									bot.sendMessage(channel, nick + ": " + args[0] + " is n0t a valid argument");
									return;
								}
								//Only do it if requests are scheduled to end
								if (!requests.isGoingToEnd()) {
									try {
										time = Integer.parseInt(args[1]);
									} catch (Exception e) {
										bot.sendMessage(channel, "please give me valid numbers " + nick);
										return;
									}
									if (!requests.areOpen()) {
										requests.turnOnRequests(time);
										return;
									}
									bot.sendMessage(channel, nick + ": " + "requests are already 0pen");
									return;
								}
								bot.sendMessage(channel, nick + ": " + "requests are already scheduled t0 end");
								return;
							} else if (input.equals("-on")) {
								if (!requests.areOpen()) {
									requests.turnOnRequests();
									return;
								}
								bot.sendMessage(channel, nick + ": " + "requests are already 0pen");
								return;
							}
							bot.sendMessage(channel, nick + ": " + input + " is n0t a valid argument");
							return;
						}
						bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
						return;
					//Check for -off argument
					} else if (input.toLowerCase().startsWith("-off")) {
						if (hasOp) {
							//Check if a time is specified
							if (input.contains(" ")) {
								String[] args = input.split(" ");
								int time = 1;
								if (!args[0].equals("-off")) {
									bot.sendMessage(channel, nick + ": " + args[0] + " is n0t a valid argument");
									return;
								}
								//Only do it if requests are scheduled to end
								if(!requests.isGoingToEnd()) {
									try {
										time = Integer.parseInt(args[1]);
									} catch (Exception e) {
										bot.sendMessage(channel, "please give me valid numbers " + nick);
										return;
									}
									if (requests.areOpen()) {
										requests.turnOffRequests(time);
										return;
									}
									bot.sendMessage(channel, nick + ": " + "requests are already cl0sed");
									return;
								}
								bot.sendMessage(channel, nick + ": " + "requests are already scheduled t0 end");
								return;
							} else if (input.equals("-off")) {
								if (requests.areOpen()) {
									requests.turnOffRequests();
									return;
								}
								bot.sendMessage(channel, nick + ": " + "requests are already cl0sed");
								return;
							}
							bot.sendMessage(channel, nick + ": " + input + " is n0t a valid argument");
							return;
						}
						bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
						return;
					//Check for -clear argument
					} else if (input.toLowerCase().startsWith("-clear") || input.toLowerCase().startsWith("-c")) {
						if (hasOp) {
							//Check if a time is specified
							if (input.contains(" ")) {
								String[] args = input.split(" ");
								if (!args[0].equals("-clear") || args[0].equals("-c")) {
									bot.sendMessage(channel, nick + ": " + args[0] + " is n0t a valid argument");
									return;
								}
								if (args[1].equals("all")) {
									requests.clearRequests();
									requests.clearUsers();
									bot.sendMessage(channel, nick + ": " + "all requests have been cleared");
									return;
								} else if (args[1].equals("songs")) {
									requests.clearRequests();
									bot.sendMessage(channel, nick + ": " + "all songs have been cleared");
									return;
								} else if (args[1].equals("users")) {
									requests.clearUsers();
									bot.sendMessage(channel, nick + ": " + "all users have been cleared");
									return;
								}
								bot.sendMessage(channel, nick + ": " + args[1] + " is n0t a valid argument");
								return;
							} else if (input.equals("-clear") || input.equals("-c")) {
								requests.clearRequests();
								requests.clearUsers();
								bot.sendMessage(channel, nick + ": " + "all requests have been cleared");
								return;
							}
							bot.sendMessage(channel, nick + ": " + input + " is n0t a valid argument");
							return;
						}
						bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
						return;
					}
					if (requests.areOpen()) {
						if (requests.countUserReqests(sender.getHostmask()) < 5) {
							if (!requests.hasBeenRequested(input)) {
								requests.addRequest(input, sender);
								bot.sendMessage(channel, nick + ": " + "y0ur request has been added t0 the list");
								return;
							}
							bot.sendMessage(channel, nick + ": " + "that s0ng has already been requested");
							return;
						}
						bot.sendMessage(channel, nick + ": " + "y0u have already made 5 requests ");
						return;
					}
					bot.sendMessage(channel, "requests are currenly " + Colors.BOLD + Colors.RED + "CLOSED");
					return;
				}
				bot.sendMessage(channel, nick + ": " + StringHelper.getCommand("req"));
				return;
			}
			return;
		}
		return;
	}
}
