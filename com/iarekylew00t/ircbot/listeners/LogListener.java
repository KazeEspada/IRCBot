package com.iarekylew00t.ircbot.listeners;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.ModeEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.pircbotx.hooks.events.ReconnectEvent;
import org.pircbotx.hooks.events.ServerPingEvent;

import com.iarekylew00t.ircbot.handlers.LogHandler;
import com.iarekylew00t.managers.DataManager;

public class LogListener extends ListenerAdapter {
	LogHandler logger = DataManager.logHandler;

	@Override
	public void onConnect(ConnectEvent event) {
		logger.notice("CONNECTED TO SERVER");
	}
	
	@Override
	public void onDisconnect(DisconnectEvent event) {
		logger.warning("DISCONNECTED FROM SERVER");
	}
	
	@Override
	public void onReconnect(ReconnectEvent event) {
		logger.notice("RECONNECTED TO SERVER");
	}
	
	@Override
	public void onServerPing(ServerPingEvent event) {
		logger.info("<<< PING " + event.getResponse());
	}
	
	@Override
	public void onJoin(JoinEvent event) {
		logger.notice(event.getUser().getNick() + "!" + event.getUser().getLogin() + "@" + event.getUser().getHostmask() + " JOINED " + event.getChannel().getName());
	}
	
	@Override
	public void onPart(PartEvent event) {
		logger.notice(event.getUser().getNick() + "!" + event.getUser().getLogin() + "@" + event.getUser().getHostmask() + " PARTED " + event.getChannel().getName());
	}
	
	@Override
	public void onQuit(QuitEvent event) {
		logger.notice(event.getUser().getNick() + "!" + event.getUser().getLogin() + "@" + event.getUser().getHostmask() + " QUIT " + "(" + event.getReason() + ")");
	}
	
	@Override
	public void onAction(ActionEvent event) {
		logger.info(event.getUser().getNick() + "!" + event.getUser().getLogin() + " ACTION " + event.getAction());
	}
	
	@Override
	public void onMode(ModeEvent event) {
		if (event.getUser().getNick().equals("NickServ") || event.getUser().getNick().equals("ChanServ")) {
			logger.info(event.getUser().getNick() + event.getUser().getLogin() + " SET MODE " + event.getMode());
		} else {
			logger.info(event.getUser().getNick() + "!" + event.getUser().getLogin() + " SET MODE " + event.getMode());
		}
	}
	
	@Override
	public void onNickChange(NickChangeEvent event) {
		logger.info(event.getOldNick() + "!" + event.getUser().getLogin() + " SET NICK " + event.getNewNick());
	}
	
	@Override
	public void onMessage(MessageEvent event) {
		logger.log(event.getUser().getNick() + "!" + event.getUser().getLogin() +  " (" + event.getChannel().getName() + "): " + event.getMessage());
	}
	
	@Override
	public void onNotice(NoticeEvent event) {
		if (event.getUser().getNick().equals("NickServ") || event.getUser().getNick().equals("ChanServ")) {
			logger.info(event.getUser().getNick() + event.getUser().getLogin() + " NOTICE " + event.getNotice());
		} else {
			logger.info(event.getUser().getNick() + "!" + event.getUser().getLogin() + " NOTICE " + event.getNotice());
		}
	}
	
	@Override
	public void onKick(KickEvent event) {
		logger.notice(event.getSource().getNick() + "!" + event.getSource().getLogin() + " KICKED " + event.getRecipient().getNick() + "!" + event.getRecipient().getLogin() + "(" + event.getReason() + ")");
	}
}
