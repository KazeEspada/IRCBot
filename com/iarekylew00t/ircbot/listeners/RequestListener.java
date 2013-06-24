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
		String message = event.getMessage();
		String nick = sender.getNick();
		String input = "";
		boolean hasOp = channel.isOp(sender);

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
									event.respond(args[0] + " is n0t a valid argument");
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
									event.respond("requests are already 0pen");
									return;
								}
								event.respond("requests are already scheduled t0 end");
								return;
							} else if (input.equals("-on")) {
								if (!requests.areOpen()) {
									requests.turnOnRequests();
									return;
								}
								event.respond("requests are already 0pen");
								return;
							}
							event.respond(input + " is n0t a valid argument");
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
									event.respond(args[0] + " is n0t a valid argument");
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
									event.respond("requests are already cl0sed");
									return;
								}
								event.respond("requests are already scheduled t0 end");
								return;
							} else if (input.equals("-off")) {
								if (requests.areOpen()) {
									requests.turnOffRequests();
									return;
								}
								event.respond("requests are already cl0sed");
								return;
							}
							event.respond(input + " is n0t a valid argument");
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
									event.respond(args[0] + " is n0t a valid argument");
									return;
								}
								if (args[1].equals("all")) {
									requests.clearRequests();
									requests.clearUsers();
									event.respond("all requests have been cleared");
									return;
								} else if (args[1].equals("songs")) {
									requests.clearRequests();
									event.respond("all songs have been cleared");
									return;
								} else if (args[1].equals("users")) {
									requests.clearUsers();
									event.respond("all users have been cleared");
									return;
								}
								event.respond(args[1] + " is n0t a valid argument");
								return;
							} else if (input.equals("-clear") || input.equals("-c")) {
								requests.clearRequests();
								requests.clearUsers();
								event.respond("all requests have been cleared");
								return;
							}
							event.respond(input + " is n0t a valid argument");
							return;
						}
						bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
						return;
					}
					if (requests.areOpen()) {
						if (requests.countUserReqests(sender.getHostmask()) < 5) {
							if (!requests.hasBeenRequested(input)) {
								requests.addRequest(input, sender);
								event.respond("y0ur request has been added t0 the list");
								return;
							}
							event.respond("that s0ng has already been requested");
							return;
						}
						event.respond("y0u have already made 5 requests ");
						return;
					}
					bot.sendMessage(channel, "requests are currenly " + Colors.BOLD + Colors.RED + "CLOSED");
					return;
				}
				event.respond(StringHelper.getCommand("req"));
				return;
			}
			return;
		}
		return;
	}
}
