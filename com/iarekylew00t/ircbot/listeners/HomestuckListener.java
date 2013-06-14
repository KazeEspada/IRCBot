package com.iarekylew00t.ircbot.listeners;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.iarekylew00t.helpers.StringHelper;
import com.iarekylew00t.ircbot.handlers.HomestuckHandler;
import com.iarekylew00t.ircbot.handlers.QuoteHandler;

public class HomestuckListener extends ListenerAdapter {
	private HomestuckHandler hsHandler = new HomestuckHandler(1);
	private QuoteHandler quote = new QuoteHandler();
	private String PAGE_BASE = "?s=6&p=00";
	private String MSPA_BASE = "http://www.mspaintadventures.com/";

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
			if (message.toLowerCase().startsWith("$latest")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					event.respond(MSPA_BASE + PAGE_BASE + hsHandler.getLatestPage());
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, StringHelper.getCommand("latest"));
				return;
				
			/* --- MSPAWIKI --- */
			//TODO IMPROVE HOMESTUCK SEARCH
			} else if (message.toLowerCase().startsWith("$mspawiki")) {
				input = message.substring(9);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					event.respond(hsHandler.searchWiki(input));
					return;
				}
				bot.sendMessage(channel, StringHelper.getCommand("mspawiki"));
				return;
				
			/* --- MSPA --- */	
			} else if (message.toLowerCase().startsWith("$mspa") && !(message.toLowerCase().startsWith("$mspawiki"))) {
				input = message.substring(5);
				if (StringHelper.isEmpty(input)) {
					event.respond("http://www.mspaintadventures.com/");
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, StringHelper.getCommand("mspa"));
				return;
				
			/* --- GET PAGE --- */
			} else if (message.toLowerCase().startsWith("$page")) {
				input = message.substring(5);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					int page = 0;
					try {
						page = Integer.parseInt(input);
						if (page == 4299 || page == 4938 || page == 4988) {
							event.respond("that page d0es n0t exist");
							return;
						}
						event.respond(MSPA_BASE + PAGE_BASE + hsHandler.getPage(page));
						return;
					} catch (Exception e) {
						bot.sendMessage(channel, "please give me valid numbers " + nick);
						return;
					}
				}
				event.respond(MSPA_BASE + PAGE_BASE + hsHandler.getPage());
				return;
			
			/* --- QUOTE --- */
			} else if (message.toLowerCase().startsWith("$quote")) {
				input = message.substring(6);
				if (!StringHelper.isEmpty(input)) {
					if (!input.startsWith(" ")) {
						return;
					}
					input = StringHelper.setString(input);
					if (input.contains(" ")) {
						String[] args = input.split(" ");
						int quoteNum = 1;
						try {
							quoteNum = Integer.parseInt(args[1]);
						} catch (Exception e) {
							bot.sendMessage(channel, "please give me valid numbers " + nick);
							return;
						}
						if (args[0].equalsIgnoreCase("John") || args[0].equalsIgnoreCase("ectoBiologist") || args[0].equalsIgnoreCase("ghostyTrickster")) {
							bot.sendMessage(channel, quote.getJohnQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Rose") || args[0].equalsIgnoreCase("tentacleTherapist")) {
							bot.sendMessage(channel, quote.getRoseQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Dave") || args[0].equalsIgnoreCase("turntechGodhead")) {
							bot.sendMessage(channel, quote.getDaveQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Jade") || args[0].equalsIgnoreCase("gardenGnostic")) {
							bot.sendMessage(channel, quote.getJadeQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Jane") || args[0].equalsIgnoreCase("gutsyGumshoe")) {
							bot.sendMessage(channel, quote.getJaneQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Roxy") || args[0].equalsIgnoreCase("tipsyGnostalgic")) {
							bot.sendMessage(channel, quote.getRoxyQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Dirk") || args[0].equalsIgnoreCase("timeausTestified")) {
							bot.sendMessage(channel, quote.getDirkQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Jake") || args[0].equalsIgnoreCase("golgothasTerror")) {
							bot.sendMessage(channel, quote.getJakeQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("AA") || args[0].equalsIgnoreCase("Aradia") || args[0].equalsIgnoreCase("Ara") || args[0].equalsIgnoreCase("Aradiabot") || args[0].equalsIgnoreCase("apocalypseArisen")) {
							bot.sendMessage(channel, quote.getAradiaQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("AT") || args[0].equalsIgnoreCase("Tavros") || args[0].equalsIgnoreCase("Tav") || args[0].equalsIgnoreCase("adiosToreador")) {
							bot.sendMessage(channel, quote.getTavrosQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("TA") || args[0].equalsIgnoreCase("Sollux") || args[0].equalsIgnoreCase("Sol") || args[0].equalsIgnoreCase("twinArmageddons")) {
							bot.sendMessage(channel, quote.getSolluxQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("CCG") || args[0].equalsIgnoreCase("PCG") || args[0].equalsIgnoreCase("FCG") || args[0].equalsIgnoreCase("CG") || args[0].equalsIgnoreCase("Karkat") || args[0].equalsIgnoreCase("Kar") || args[0].equalsIgnoreCase("carcinoGeneticist")) {
							bot.sendMessage(channel, quote.getKarkatQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("AC") || args[0].equalsIgnoreCase("Nepeta") || args[0].equalsIgnoreCase("Nep") || args[0].equalsIgnoreCase("arsenicCatnip")) {
							bot.sendMessage(channel, quote.getNepetaQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("GA") || args[0].equalsIgnoreCase("Kanaya") || args[0].equalsIgnoreCase("Kan") || args[0].equalsIgnoreCase("grimAuxiliatrix")) {
							bot.sendMessage(channel, quote.getKanayaQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("GC") || args[0].equalsIgnoreCase("Terezi") || args[0].equalsIgnoreCase("Ter") || args[0].equalsIgnoreCase("gallowsCalibrator")) {
							bot.sendMessage(channel, quote.getTereziQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("AG") || args[0].equalsIgnoreCase("Vriska") || args[0].equalsIgnoreCase("Vrisk") || args[0].equalsIgnoreCase("Vris") || args[0].equalsIgnoreCase("arachnidsGrip")) {
							bot.sendMessage(channel, quote.getVriskaQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("CT") || args[0].equalsIgnoreCase("Equius") || args[0].equalsIgnoreCase("Equ") || args[0].equalsIgnoreCase("Eq") || args[0].equalsIgnoreCase("centaursTesticle")) {
							bot.sendMessage(channel, quote.getEquiusQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("TC") || args[0].equalsIgnoreCase("Gamzee") || args[0].equalsIgnoreCase("Gam") || args[0].equalsIgnoreCase("terminallyCapricious")) {
							bot.sendMessage(channel, quote.getGamzeeQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("CA") || args[0].equalsIgnoreCase("Eridan") || args[0].equalsIgnoreCase("Eri") || args[0].equalsIgnoreCase("caligulasAquarium")) {
							bot.sendMessage(channel, quote.getEridanQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("CC") || args[0].equalsIgnoreCase("Feferi") || args[0].equalsIgnoreCase("Fef") || args[0].equalsIgnoreCase("cuttlefishCuller")) {
							bot.sendMessage(channel, quote.getFeferiQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Damara")) {
							bot.sendMessage(channel, quote.getDamaraQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Rufioh")) {
							bot.sendMessage(channel, quote.getRufiohQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Mituna") || args[0].equalsIgnoreCase("Tuna")) {
							bot.sendMessage(channel, quote.getMitunaQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Kankri") || args[0].equalsIgnoreCase("Kan")) {
							bot.sendMessage(channel, quote.getKankriQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Meulin")) {
							bot.sendMessage(channel, quote.getMeulinQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Porrim")) {
							bot.sendMessage(channel, quote.getPorrimQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Latula") || args[0].equalsIgnoreCase("Tula")) {
							bot.sendMessage(channel, quote.getLatulaQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Aranea")) {
							bot.sendMessage(channel, quote.getAraneaQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Horuss")) {
							bot.sendMessage(channel, quote.getHorussQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Kurloz")) {
							bot.sendMessage(channel, quote.getKurlozQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Cronus")) {
							bot.sendMessage(channel, quote.getCronusQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Meenah")) {
							bot.sendMessage(channel, quote.getMeenahQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("HIC") || args[0].equalsIgnoreCase(")(IC")) {
							bot.sendMessage(channel, quote.getHICQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Davesprite")) {
							bot.sendMessage(channel, quote.getDavespriteQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Jadesprite")) {
							bot.sendMessage(channel, quote.getJadespriteQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Tavris") || args[0].equalsIgnoreCase("Tavrisprite")) {
							bot.sendMessage(channel, quote.getTavrispriteQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Caliborn") || args[0].equalsIgnoreCase("Cali")) {
							bot.sendMessage(channel, quote.getCalibornQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Calliope") || args[0].equalsIgnoreCase("Calli")) {
							bot.sendMessage(channel, quote.getCalliopeQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Equiusprite") || args[0].equalsIgnoreCase("Arquiusprite") || args[0].equalsIgnoreCase("Arquius")) {
							bot.sendMessage(channel, quote.getArquiuspriteQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Cal") || args[0].equalsIgnoreCase("Calsprite")) {
							bot.sendMessage(channel, quote.getCalspriteQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Dragon") || args[0].equalsIgnoreCase("Dragonsprite")) {
							bot.sendMessage(channel, quote.getDragonspriteQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Nanna") || args[0].equalsIgnoreCase("Nannasprite")) {
							bot.sendMessage(channel, quote.getNannaspriteQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Doc")) {
							bot.sendMessage(channel, quote.getDocQuote(quoteNum));
							return;
						} else if (args[0].equalsIgnoreCase("Andrew") || args[0].equalsIgnoreCase("Hussie") || args[0].equalsIgnoreCase("AH")) {
							bot.sendMessage(channel, quote.getHussieQuote(quoteNum));
							return;
						}
						bot.sendMessage(channel, "im s0rry but i d0 n0t have that character");
						return;
					}
					if (input.equalsIgnoreCase("John") || input.equalsIgnoreCase("ectoBiologist") || input.equalsIgnoreCase("ghostyTrickster")) {
						bot.sendMessage(channel, quote.getJohnQuote());
						return;
					} else if (input.equalsIgnoreCase("Rose") || input.equalsIgnoreCase("tentacleTherapist")) {
						bot.sendMessage(channel, quote.getRoseQuote());
						return;
					} else if (input.equalsIgnoreCase("Dave") || input.equalsIgnoreCase("turntechGodhead")) {
						bot.sendMessage(channel, quote.getDaveQuote());
						return;
					} else if (input.equalsIgnoreCase("Jade") || input.equalsIgnoreCase("gardenGnostic")) {
						bot.sendMessage(channel, quote.getJadeQuote());
						return;
					} else if (input.equalsIgnoreCase("Jane") || input.equalsIgnoreCase("gutsyGumshoe")) {
						bot.sendMessage(channel, quote.getJaneQuote());
						return;
					} else if (input.equalsIgnoreCase("Roxy") || input.equalsIgnoreCase("tipsyGnostalgic")) {
						bot.sendMessage(channel, quote.getRoxyQuote());
						return;
					} else if (input.equalsIgnoreCase("Dirk") || input.equalsIgnoreCase("timeausTestified")) {
						bot.sendMessage(channel, quote.getDirkQuote());
						return;
					} else if (input.equalsIgnoreCase("Jake") || input.equalsIgnoreCase("golgothasTerror")) {
						bot.sendMessage(channel, quote.getJakeQuote());
						return;
					} else if (input.equalsIgnoreCase("AA") || input.equalsIgnoreCase("Aradia") || input.equalsIgnoreCase("Ara") || input.equalsIgnoreCase("Aradiabot") || input.equalsIgnoreCase("apocalypseArisen")) {
						bot.sendMessage(channel, quote.getAradiaQuote());
						return;
					} else if (input.equalsIgnoreCase("AT") || input.equalsIgnoreCase("Tavros") || input.equalsIgnoreCase("Tav") || input.equalsIgnoreCase("adiosToreador")) {
						bot.sendMessage(channel, quote.getTavrosQuote());
						return;
					} else if (input.equalsIgnoreCase("TA") || input.equalsIgnoreCase("Sollux") || input.equalsIgnoreCase("Sol") || input.equalsIgnoreCase("twinArmageddons")) {
						bot.sendMessage(channel, quote.getSolluxQuote());
						return;
					} else if (input.equalsIgnoreCase("CCG") || input.equalsIgnoreCase("PCG") || input.equalsIgnoreCase("FCG") || input.equalsIgnoreCase("CG") || input.equalsIgnoreCase("Karkat") || input.equalsIgnoreCase("Kar") || input.equalsIgnoreCase("carcinoGeneticist")) {
						bot.sendMessage(channel, quote.getKarkatQuote());
						return;
					} else if (input.equalsIgnoreCase("AC") || input.equalsIgnoreCase("Nepeta") || input.equalsIgnoreCase("Nep") || input.equalsIgnoreCase("arsenicCatnip")) {
						bot.sendMessage(channel, quote.getNepetaQuote());
						return;
					} else if (input.equalsIgnoreCase("GA") || input.equalsIgnoreCase("Kanaya") || input.equalsIgnoreCase("Kan") || input.equalsIgnoreCase("grimAuxiliatrix")) {
						bot.sendMessage(channel, quote.getKanayaQuote());
						return;
					} else if (input.equalsIgnoreCase("GC") || input.equalsIgnoreCase("Terezi") || input.equalsIgnoreCase("Ter") || input.equalsIgnoreCase("gallowsCalibrator")) {
						bot.sendMessage(channel, quote.getTereziQuote());
						return;
					} else if (input.equalsIgnoreCase("AG") || input.equalsIgnoreCase("Vriska") || input.equalsIgnoreCase("Vrisk") || input.equalsIgnoreCase("Vris") || input.equalsIgnoreCase("arachnidsGrip")) {
						bot.sendMessage(channel, quote.getVriskaQuote());
						return;
					} else if (input.equalsIgnoreCase("CT") || input.equalsIgnoreCase("Equius") || input.equalsIgnoreCase("Equ") || input.equalsIgnoreCase("Eq") || input.equalsIgnoreCase("centaursTesticle")) {
						bot.sendMessage(channel, quote.getEquiusQuote());
						return;
					} else if (input.equalsIgnoreCase("TC") || input.equalsIgnoreCase("Gamzee") || input.equalsIgnoreCase("Gam") || input.equalsIgnoreCase("terminallyCapricious")) {
						bot.sendMessage(channel, quote.getGamzeeQuote());
						return;
					} else if (input.equalsIgnoreCase("CA") || input.equalsIgnoreCase("Eridan") || input.equalsIgnoreCase("Eri") || input.equalsIgnoreCase("caligulasAquarium")) {
						bot.sendMessage(channel, quote.getEridanQuote());
						return;
					} else if (input.equalsIgnoreCase("CC") || input.equalsIgnoreCase("Feferi") || input.equalsIgnoreCase("Fef") || input.equalsIgnoreCase("cuttlefishCuller")) {
						bot.sendMessage(channel, quote.getFeferiQuote());
						return;
					} else if (input.equalsIgnoreCase("Damara")) {
						bot.sendMessage(channel, quote.getDamaraQuote());
						return;
					} else if (input.equalsIgnoreCase("Rufioh")) {
						bot.sendMessage(channel, quote.getRufiohQuote());
						return;
					} else if (input.equalsIgnoreCase("Mituna") || input.equalsIgnoreCase("Tuna")) {
						bot.sendMessage(channel, quote.getMitunaQuote());
						return;
					} else if (input.equalsIgnoreCase("Kankri") || input.equalsIgnoreCase("Kan")) {
						bot.sendMessage(channel, quote.getKankriQuote());
						return;
					} else if (input.equalsIgnoreCase("Meulin")) {
						bot.sendMessage(channel, quote.getMeulinQuote());
						return;
					} else if (input.equalsIgnoreCase("Porrim")) {
						bot.sendMessage(channel, quote.getPorrimQuote());
						return;
					} else if (input.equalsIgnoreCase("Latula") || input.equalsIgnoreCase("Tula")) {
						bot.sendMessage(channel, quote.getLatulaQuote());
						return;
					} else if (input.equalsIgnoreCase("Aranea")) {
						bot.sendMessage(channel, quote.getAraneaQuote());
						return;
					} else if (input.equalsIgnoreCase("Horuss")) {
						bot.sendMessage(channel, quote.getHorussQuote());
						return;
					} else if (input.equalsIgnoreCase("Kurloz")) {
						bot.sendMessage(channel, quote.getKurlozQuote());
						return;
					} else if (input.equalsIgnoreCase("Cronus")) {
						bot.sendMessage(channel, quote.getCronusQuote());
						return;
					} else if (input.equalsIgnoreCase("Meenah")) {
						bot.sendMessage(channel, quote.getMeenahQuote());
						return;
					} else if (input.equalsIgnoreCase("HIC") || input.equalsIgnoreCase(")(IC")) {
						bot.sendMessage(channel, quote.getHICQuote());
						return;
					} else if (input.equalsIgnoreCase("Davesprite")) {
						bot.sendMessage(channel, quote.getDavespriteQuote());
						return;
					} else if (input.equalsIgnoreCase("Jadesprite")) {
						bot.sendMessage(channel, quote.getJadespriteQuote());
						return;
					} else if (input.equalsIgnoreCase("Tavris") || input.equalsIgnoreCase("Tavrisprite")) {
						bot.sendMessage(channel, quote.getTavrispriteQuote());
						return;
					} else if (input.equalsIgnoreCase("Caliborn") || input.equalsIgnoreCase("Cali")) {
						bot.sendMessage(channel, quote.getCalibornQuote());
						return;
					} else if (input.equalsIgnoreCase("Calliope") || input.equalsIgnoreCase("Calli")) {
						bot.sendMessage(channel, quote.getCalliopeQuote());
						return;
					} else if (input.equalsIgnoreCase("Equiusprite") || input.equalsIgnoreCase("Arquiusprite") || input.equalsIgnoreCase("Arquius")) {
						bot.sendMessage(channel, quote.getArquiuspriteQuote());
						return;
					} else if (input.equalsIgnoreCase("Cal") || input.equalsIgnoreCase("Calsprite")) {
						bot.sendMessage(channel, quote.getCalspriteQuote());
						return;
					} else if (input.equalsIgnoreCase("Dragon") || input.equalsIgnoreCase("Dragonsprite")) {
						bot.sendMessage(channel, quote.getDragonspriteQuote());
						return;
					} else if (input.equalsIgnoreCase("Nanna") || input.equalsIgnoreCase("Nannasprite")) {
						bot.sendMessage(channel, quote.getNannaspriteQuote());
						return;
					} else if (input.equalsIgnoreCase("Doc")) {
						bot.sendMessage(channel, quote.getDocQuote());
						return;
					} else if (input.equalsIgnoreCase("Andrew") || input.equalsIgnoreCase("Hussie") || input.equalsIgnoreCase("AH")) {
						bot.sendMessage(channel, quote.getHussieQuote());
						return;
					}
					bot.sendMessage(channel, "im s0rry but i d0 n0t have that character");
					return;
				}
				bot.sendMessage(channel, StringHelper.getCommand("quote"));
				return;
				
			/* --- CHECK UPDATE --- */
			} else if (message.toLowerCase().startsWith("$update")) {
				input = message.substring(7);
				if (StringHelper.isEmpty(input)) {
					if (hsHandler.checkUpdate()) {
						bot.sendMessage(channel, Colors.GREEN + Colors.BOLD + "-- THERE IS AN UPDATE --");
						return;
					}
					bot.sendMessage(channel, "there is n0 update"); 
					return;
				}
				if (!input.startsWith(" ")) {
					return;
				}
				bot.sendMessage(channel, StringHelper.getCommand("update"));
				return;
				
			/* --- HOMESTUCK SEARCH --- */
			} else if (message.toLowerCase().startsWith("$search")) {
				input = message.substring(7);
				if (!StringHelper.isEmpty(input)) {
					input = StringHelper.setString(input);
					event.respond(hsHandler.searchPage(input));
					return;
				}
				bot.sendMessage(channel, StringHelper.getCommand("search"));
				return;
			}
			return;
		}
		return;
	}
}
