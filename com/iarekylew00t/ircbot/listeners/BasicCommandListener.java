package com.iarekylew00t.ircbot.listeners;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.encryption.ByteGenerator;
import com.iarekylew00t.encryption.Encoder;
import com.iarekylew00t.encryption.Encryptor;
import com.iarekylew00t.encryption.HashGenerator;
import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.managers.DataManager;

public class BasicCommandListener extends ListenerAdapter {
    private final String[] eightBall = {"it is certain", "it is decidedly s0", "yes - definitely", "y0u may rely 0n it", "as i see it, yes", "m0st likely", "0utl00k g00d", "yes", "signs p0int t0 yes", "reply hazy, try again", "ask again later", "better not tell y0u n0w", "cann0t predict n0w", "c0ncentrate and ask again", "d0nt c0unt 0n it", "my reply is n0", "my s0urces say n0", "very d0ubtful"};
    private final String[] deadRemarks = {"0_0", "why w0uld i d0 that?", "why w0uld i do that when im already dead?", "that w0uld be pretty stupid 0f me t0 d0...", "id rather n0t...", "im n0t pr0grammed t0 d0 that", "rude much?", "can u n0t"};
    private final String[] denyRemarks = {"0_0", "im n0t all0wed t0 d0 that", "im n0t pr0grammed t0 d0 that", "i have been t0ld that i cann0t d0 that", "im n0t all0wed t0 let y0u d0 that", "can u n0t"};
    private final String[] blockRemarks = {"deflects attack", "m0ves", "slaps y0u", "ign0res it", "glares at y0u"};
    private final String[] pokeRemarks = {"p0kes herself", "p0kes y0u instead", "ign0res it", "why w0uld i p0ke myself?", "0_0"};
    private String[] cmdList = DataManager.commandList.keySet().toArray(new String[0]);
    
	@Override
	public void onMessage(MessageEvent event) {
		PircBotX bot = event.getBot();
		User sender = event.getUser();
		Channel channel = event.getChannel();
		String message = event.getMessage();
		String nick = sender.getNick();
		String input = "";
		
		/* === CHECK FOR COMMAND SYMBOL === */
		if(message.startsWith("$")) {	
			
			/* --- COMMANDS --- */
			if (message.toLowerCase().startsWith("$commands")) {
				input = message.substring(9);
				if (StringHelper.isEmpty(input)) {
					String cmdString = "";
					Arrays.sort(cmdList);
					for (int i = 0; i < cmdList.length; i++) {
						if (i != cmdList.length-1) {
							cmdString += cmdList[i] + ", ";
						} else {
							cmdString += cmdList[i];
						}
					}
					event.respond(cmdString);
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("commands"));
				return;
				
			/* --- HELP --- */
			} else if (message.toLowerCase().startsWith("$help")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (DataManager.commandList.containsKey(input)) {
						event.respond(StringHelper.getCommand(input));
						return;
					}
					bot.sendMessage(channel, "thats n0t a c0mmand");
					return;
				}
				event.respond(StringHelper.getCommand("help"));
				return;
				
			/* --- 8BALL --- */
			} else if (message.toLowerCase().startsWith("$8ball")) {
				input = message.substring(6);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					event.respond(getRandomOutcome(eightBall));
					return;
				}
				event.respond(StringHelper.getCommand("8ball"));
				return;
				
			/* --- DATE --- */
			} else if (message.toLowerCase().startsWith("$date")) {
				input = message.substring(5);
				if (StringHelper.isEmpty(input)) {
					String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
					bot.sendMessage(channel, "t0days date is: " + timeStamp);
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("date"));
				return;
				
			/* --- ENCRYPT --- */
			} else if (message.toLowerCase().startsWith("$encrypt")) {
				input = message.substring(8);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.contains(" ")) {
						String[] args = input.split(" ");
						String data = "";
						boolean isFirst = true;
						for (int i = 1; i < args.length; i++) {
							if (isFirst) {
								data += args[i];
								isFirst = false;
							} else {
								data += " " + args[i];
							}
						}
						try {
							if (args[0].equalsIgnoreCase("AES")) {
								event.respond(Encryptor.encryptAES(data));
								return;
							} else if (args[0].equalsIgnoreCase("DES")) {
								event.respond(Encryptor.encryptDES(data));
								return;
							} else if (args[0].equalsIgnoreCase("3DES") || args[0].equalsIgnoreCase("TripleDES") || args[0].equalsIgnoreCase("3-DES") || args[0].equalsIgnoreCase("DES3")  || args[0].equalsIgnoreCase("DES-3") || args[0].equalsIgnoreCase("DESede")) {
								event.respond(Encryptor.encrypt3DES(data));
								return;
							} else if (args[0].equalsIgnoreCase("Blowfish")) {
								event.respond(Encryptor.encryptBlowfish(data));
								return;
							} else if (args[0].equalsIgnoreCase("RC2") || args[0].equalsIgnoreCase("RC-2")) {
								event.respond(Encryptor.encryptRC2(data));
								return;
							}
						} catch (Exception e) {
							event.respond(Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not encrypt data");
							e.printStackTrace();
							DataManager.exception = e;
							return;
						}
					}
					bot.sendMessage(channel, "im s0rry but i d0nt kn0w that alg0rithm");
					return;
				}
				event.respond(StringHelper.getCommand("encrypt"));
				return;
				
			/* --- DECRYPT --- */
			} else if (message.toLowerCase().startsWith("$decrypt")) {
				input = message.substring(8);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.contains(" ")) {
						String[] args = input.split(" ");
						String data = "";
						boolean isFirst = true;
						for (int i = 1; i < args.length; i++) {
							if (isFirst) {
								data += args[i];
								isFirst = false;
							} else {
								data += " " + args[i];
							}
						}
						try {
							if (args[0].equalsIgnoreCase("AES")) {
								event.respond(Encryptor.decryptAES(data));
								return;
							} else if (args[0].equalsIgnoreCase("DES")) {
								event.respond(Encryptor.decryptDES(data));
								return;
							} else if (args[0].equalsIgnoreCase("3DES") || args[0].equalsIgnoreCase("TripleDES") || args[0].equalsIgnoreCase("3-DES") || args[0].equalsIgnoreCase("DES3")  || args[0].equalsIgnoreCase("DES-3") || args[0].equalsIgnoreCase("DESede")) {
								event.respond(Encryptor.decrypt3DES(data));
								return;
							} else if (args[0].equalsIgnoreCase("Blowfish")) {
								event.respond(Encryptor.decryptBlowfish(data));
								return;
							} else if (args[0].equalsIgnoreCase("RC2") || args[0].equalsIgnoreCase("RC-2")) {
								event.respond(Encryptor.decryptRC2(data));
								return;
							}
						} catch (Exception e) {
							event.respond(Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not decrypt data");
							e.printStackTrace();
							DataManager.exception = e;
							return;
						}
					}
					bot.sendMessage(channel, "im s0rry but i d0nt kn0w that alg0rithm");
					return;
				}
				event.respond(StringHelper.getCommand("decrypt"));
				return;
				
			/* --- ENCODE --- */
			} else if (message.toLowerCase().startsWith("$encode")) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.contains(" ")) {
						String[] args = input.split(" ");
						String data = "";
						boolean isFirst = true;
						for (int i = 1; i < args.length; i++) {
							if (isFirst) {
								data += args[i];
								isFirst = false;
							} else {
								data += " " + args[i];
							}
						}
						try {
							if (args[0].equalsIgnoreCase("BASE64") || args[0].equalsIgnoreCase("BASE-64")) {
								event.respond(Encoder.encodeBase64(data));
								return;
							}
						} catch (Exception e) {
							event.respond(Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not encode data");
							e.printStackTrace();
							DataManager.exception = e;
							return;
						}
					}
					bot.sendMessage(channel, "im s0rry but i d0nt kn0w that alg0rithm");
					return;
				}
				event.respond(StringHelper.getCommand("encode"));
				return;
				
			/* --- DECRYPT --- */
			} else if (message.toLowerCase().startsWith("$decode")) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.contains(" ")) {
						String[] args = input.split(" ");
						String data = "";
						boolean isFirst = true;
						for (int i = 1; i < args.length; i++) {
							if (isFirst) {
								data += args[i];
								isFirst = false;
							} else {
								data += " " + args[i];
							}
						}
						try {
							if (args[0].equalsIgnoreCase("BASE64") || args[0].equalsIgnoreCase("BASE-64")) {
								event.respond(Encoder.decodeBase64(data));
								return;
							}
						} catch (Exception e) {
							event.respond(Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not decode data");
							e.printStackTrace();
							DataManager.exception = e;
							return;
						}
					}
					bot.sendMessage(channel, "im s0rry but i d0nt kn0w that alg0rithm");
					return;
				}
				event.respond(StringHelper.getCommand("decode"));
				return;
				
			/* --- HASH --- */
			} else if (message.toLowerCase().startsWith("$hash")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.contains(" ")) {
						String[] args = input.split(" ");
						String data = "";
						boolean isFirst = true;
						for (int i = 1; i < args.length; i++) {
							if (isFirst) {
								data += args[i];
								isFirst = false;
							} else {
								data += " " + args[i];
							}
						}
						try {
							if (args[0].equalsIgnoreCase("MD5") || args[0].equalsIgnoreCase("MD-5")) {
								event.respond(HashGenerator.hashMD5(data));
								return;
							} else if (args[0].equalsIgnoreCase("MD2") || args[0].equalsIgnoreCase("MD-2")) {
								event.respond(HashGenerator.hashMD2(data));
								return;
							} else if (args[0].equalsIgnoreCase("SHA1") || args[0].equalsIgnoreCase("SHA-1") || args[0].equalsIgnoreCase("SHA128") || args[0].equalsIgnoreCase("SHA-128")) {
								event.respond(HashGenerator.hashSHA1(data));
								return;
							} else if (args[0].equalsIgnoreCase("SHA256") || args[0].equalsIgnoreCase("SHA-256")) {
								event.respond(HashGenerator.hashSHA256(data));
								return;
							} else if (args[0].equalsIgnoreCase("SHA384") || args[0].equalsIgnoreCase("SHA-384")) {
								event.respond(HashGenerator.hashSHA384(data));
								return;
							} else if (args[0].equalsIgnoreCase("SHA512") || args[0].equalsIgnoreCase("SHA-512")) {
								event.respond(HashGenerator.hashSHA512(data));
								return;
							}
						} catch (Exception e) {
							event.respond(Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Could not generate hash");
							e.printStackTrace();
							DataManager.exception = e;
							return;
						}
					}
					bot.sendMessage(channel, "im s0rry but i d0nt kn0w that alg0rithm");
					return;
				}
				event.respond(StringHelper.getCommand("hash"));
				return;
				
			/* --- FAQ --- */	
			} else if (message.toLowerCase().startsWith("$faq")) {
				input = message.substring(4);
				if (StringHelper.isEmpty(input)) {
					event.respond("http://goo.gl/BTctR");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("faq"));
				return;
				
			/* --- GEARUP --- */
			} else if (message.toLowerCase().startsWith("$gearup")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, "y0u are n0w geared up " + nick);
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("gearup"));
				return;
				
			/* --- HEAL --- */	
			} else if (message.toLowerCase().startsWith("$heal")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u have been healed");
						return;
					}
					bot.sendMessage(channel, input + " has been healed");
					return;
				}
				event.respond(StringHelper.getCommand("heal"));
				return;
				
			/* --- IRC --- */	
			} else if (message.toLowerCase().startsWith("$irc")) {
				input = message.substring(4);
				if (StringHelper.isEmpty(input)) {
					event.respond("http://goo.gl/dIfQu");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("irc"));
				return;
				
			/* --- KILL --- */	
			} else if (message.toLowerCase().startsWith("$kill")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0ure n0t all0wed t0 kill y0urself " + nick);
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendMessage(channel, getRandomOutcome(deadRemarks));
						return;
					} else if (input.equalsIgnoreCase("IAreKyleW00t") || input.equalsIgnoreCase("Kyle")) {
						bot.sendMessage(channel, getRandomOutcome(denyRemarks));
						return;
					} else {
						bot.sendMessage(channel, input + " has been killed");
						return;
					}
				}
				event.respond(StringHelper.getCommand("kill"));
				return;
				
			/* --- LMTYAHS --- */	
			} else if (message.toLowerCase().startsWith("$lmtyahs")) {
				input = message.substring(8);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, "let me tell y0u ab0ut h0mestuck... http://goo.gl/XFYbz");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("lmtyahs"));
				return;
				
			/* --- PAP --- */	
			} else if (message.toLowerCase().startsWith("$pap")) {
				input = message.substring(4);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u have been papped");
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendAction(channel, "paps herself");
						return;
					} else {
						bot.sendAction(channel, "paps " + input);
						return;
					}
				}
				event.respond(StringHelper.getCommand("pap"));
				return;
				
			/* --- PING --- */
			} else if (message.toLowerCase().startsWith("$ping")) {
				input = message.substring(5);
				if (StringHelper.isEmpty(input)) {
					event.respond("p0ng");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("ping"));
				return;
				
			/* --- PLAYFLUTE --- */	
			} else if (message.toLowerCase().startsWith("$playflute")) {
				input = message.substring(10);
				if (StringHelper.isEmpty(input)) {
					event.respond("http://goo.gl/LpK89");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("playflute"));
				return;
				
			/* --- POKE --- */
			} else if (message.toLowerCase().startsWith("$poke")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendAction(channel, "p0kes y0u");
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendAction(channel, getRandomOutcome(pokeRemarks));
						return;
					}
					bot.sendAction(channel, "p0kes " + input);
					return;
				}
				event.respond(StringHelper.getCommand("poke"));
				return;
				
			/* --- RADIO --- */	
			} else if (message.toLowerCase().startsWith("$radio")) {
				input = message.substring(6);
				if (StringHelper.isEmpty(input)) {
					event.respond("http://www.mixlr.com/iarekylew00t/");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("radio"));
				return;
				
			/* --- RANDOM --- */	
			} else if (message.toLowerCase().startsWith("$random")) {
				input = message.substring(7);
				Random gen = new Random();
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					int maxNum = 0;
					try {
						maxNum = Integer.parseInt(input);
					} catch (ArithmeticException e) {
						e.printStackTrace();
						bot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Cannot handle numbers greater than 2,147,483,647");
						return;
					} catch (Exception e) {
						e.printStackTrace();
						bot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Please use valid numbers");
						return;
					}
					if (maxNum <= 0 || maxNum > 2147483647) {
						event.respond("please enter a number fr0m 1-2,147,483,647");
						return;
					}
					int randNum = gen.nextInt(maxNum);
					event.respond("" + randNum);
					return;
				}
				event.respond("" + gen.nextInt(10));
				return;
				
			/* --- SECURE RANDOM --- */	
			} else if (message.toLowerCase().startsWith("$srandom")) {
				input = message.substring(8);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					int bytes = 0;
					try {
						bytes = Integer.parseInt(input);
					} catch (ArithmeticException e) {
						e.printStackTrace();
						bot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Cannot handle numbers greater than 2,147,483,647");
						return;
					} catch (Exception e) {
						e.printStackTrace();
						bot.sendMessage(channel, Colors.RED + Colors.BOLD + "ERROR: " + Colors.NORMAL + "Please use valid numbers");
						return;
					}
					if (bytes <= 0 || bytes > 192) {
						event.respond("please enter a number fr0m 1-192");
						return;
					}
					event.respond(ByteGenerator.toHex(ByteGenerator.genRandomByte(bytes)));
					return;
				}
				event.respond(ByteGenerator.toHex(ByteGenerator.genRandomByte(8)));
				return;
				
			/* --- REVIVE --- */
			} else if (message.toLowerCase().startsWith("$random")) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u cant revive y0urself " + nick);
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendMessage(channel, "i d0nt need revived - im a r0b0t");
						return;
					} else {
						bot.sendMessage(channel, "reviving " + input);
						return;
					}
				}
				event.respond(StringHelper.getCommand("revive"));
				return;
				
			/* --- RULES --- */
			} else if (message.toLowerCase().startsWith("$rules")) {
				input = message.substring(6);
				if (StringHelper.isEmpty(input)) {
					event.respond("http://goo.gl/rb0RS (y0u better read these)");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("rules"));
				return;
				
			/* --- SERVE --- */	
			} else if (message.toLowerCase().startsWith("$serve")) {
				input = message.substring(6);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					bot.sendMessage(channel, "serving " + input);
					return;
				}
				event.respond(StringHelper.getCommand("serve"));
				return;
				
			/* --- SHOOSH --- */	
			} else if (message.toLowerCase().startsWith("$shoosh") && !(message.toLowerCase().startsWith("$shooshpap"))) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u have been sh00shed");
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendAction(channel, "sh00shes herself");
						return;
					} else {
						bot.sendAction(channel, "sh00shes " + input);
						return;
					}
				}
				event.respond(StringHelper.getCommand("shoosh"));
				return;
				
			/* --- SHOOSHPAP --- */	
			} else if (message.toLowerCase().startsWith("$shooshpap")) {
				input = message.substring(10);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u have been sh00shpapped");
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendAction(channel, "sh00shpaps herself");
						return;
					} else {
						bot.sendAction(channel, "sh00shpaps " + input);
						return;
					}
				}
				event.respond(StringHelper.getCommand("shooshpap"));
				return;
				
			/* --- SHOOT --- */
			} else if (message.toLowerCase().startsWith("$shoot")) {
				input = message.substring(6);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u just sh0t y0urself...");
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendMessage(channel, getRandomOutcome(deadRemarks));
						return;
					} else if (input.equalsIgnoreCase("IAreKyleW00t") || input.toLowerCase().startsWith("Kyle")) {
						bot.sendMessage(channel, getRandomOutcome(denyRemarks));
						return;
					} else {
						bot.sendMessage(channel, input + " has been sh0t");
						return;
					}
				}
				event.respond(StringHelper.getCommand("shoot"));
				return;
				
			/* --- SLAP --- */
			} else if (message.toLowerCase().startsWith("$slap")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u just slapped y0urself...");
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendAction(channel, getRandomOutcome(blockRemarks));
						return;
					} else {
						bot.sendAction(channel, "slaps " + input);
						return;
					}
				}
				event.respond(StringHelper.getCommand("slap"));
				return;
			
			/* --- SOURCE/GITHUB --- */
			} else if (message.toLowerCase().startsWith("$source")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					event.respond("https://github.com/IAreKyleW00t/IRCBot");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("source"));
				return;
			} else if (message.toLowerCase().startsWith("$github")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					event.respond("https://github.com/IAreKyleW00t/IRCBot");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("source"));
				return;
				
			/* --- STAB --- */
			} else if (message.toLowerCase().startsWith("$stab")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.equalsIgnoreCase(nick)) {
						bot.sendMessage(channel, "y0u have been stabbed");
						return;
					} else if (input.equalsIgnoreCase(bot.getNick())) {
						bot.sendAction(channel, getRandomOutcome(blockRemarks));
						return;
					} else {
						bot.sendMessage(channel, input + " has been stabbed");
						return;
					}
				}
				event.respond(StringHelper.getCommand("stab"));
				return;
				
			/* --- TIME --- */	
			} else if (message.toLowerCase().startsWith("$time")) {
				input = message.substring(5);
				if (StringHelper.isEmpty(input)) {
					String timeStamp = new SimpleDateFormat("hh:mm aa").format(Calendar.getInstance().getTime());
					bot.sendMessage(channel, "the current time is: " + timeStamp);
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("time"));
				return;
				
			/* --- VER --- */	
			} else if (message.toLowerCase().startsWith("$ver")) {
				input = message.substring(4);
				if (StringHelper.isEmpty(input)) {
					bot.sendMessage(channel, "Aradiabot v" + DataManager.VER);
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				event.respond(StringHelper.getCommand("ver"));
				return;
			}
			return;
		}
		return;
	}
	
	private String getRandomOutcome(String[] list) {
		Random rand = new Random();
		int randNum = rand.nextInt(list.length);
		return list[randNum];
	}
}
