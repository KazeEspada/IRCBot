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
		String input = "";
		boolean hasOp = channel.isOp(sender);

		/* === CHECK FOR COMMAND SYMBOL === */
		if(message.startsWith("$")) {	

			
			/* --- REQ ON --- */
			if (message.toLowerCase().startsWith("$reqon")) {
				input = message.substring(6);
				if (StringHelper.isEmpty(input)) {
					if (hasOp) {
						if (!requests.areOpen()) {
							requests.turnOnRequests();
							return;
						}
						event.respond("requests are already 0pen");
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("reqon"));
				return;
			
			/* --- REQ OFF --- */
			} else if (message.toLowerCase().startsWith("$reqoff")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					if (hasOp) {
						if (requests.areOpen()) {
							requests.turnOffRequests();
							return;
						}
						event.respond("requests are already cl0sed");
						return;
					}
					bot.sendMessage(channel, "y0u d0nt have permiss0n t0 use that c0mmand");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("reqoff"));
				return;
			
			/* --- REQ --- */
			} else if (message.toLowerCase().startsWith("$req") && !(message.toLowerCase().startsWith("$reqon") || message.toLowerCase().startsWith("$reqoff"))) {
				input = message.substring(4);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (requests.areOpen()) {
						if (requests.countUserReqests(sender.getHostmask()) < 3) {
							if (!requests.hasBeenRequested(input)) {
								requests.addRequest(input, sender);
								event.respond("y0ur request has been added t0 the list");
								return;
							}
							event.respond("that s0ng has already been requested");
							return;
						}
						event.respond("y0u have already made 3 requests ");
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