package com.iarekylew00t.ircbot.handlers;

import java.io.File;
import java.util.Random;

import org.pircbotx.Colors;

import com.iarekylew00t.helpers.FileHelper;

public class QuoteHandler {
	private Random rand;
	
	public QuoteHandler() {}

	public String getAradiaQuote() {
		String name = "Aradia";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getAradiaQuote(int line) {
		String name = "Aradia";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getAraneaQuote() {
		String name = "Aranea";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getAraneaQuote(int line) {
		String name = "Aranea";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getArquiuspriteQuote() {
		String name = "Arquiusprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getArquiuspriteQuote(int line) {
		String name = "Arquiusprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getCalibornQuote() {
		String name = "Caliborn";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getCalibornQuote(int line) {
		String name = "Caliborn";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getCalliopeQuote() {
		String name = "Calliope";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getCalliopeQuote(int line) {
		String name = "Calliope";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getCalspriteQuote() {
		String name = "Calsprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getCalspriteQuote(int line) {
		String name = "Calsprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getCronusQuote() {
		String name = "Cronus";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getCronusQuote(int line) {
		String name = "Cronus";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getDamaraQuote() {
		String name = "Damara";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getDamaraQuote(int line) {
		String name = "Damara";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getDaveQuote() {
		String name = "Dave";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getDaveQuote(int line) {
		String name = "Dave";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getDavespriteQuote() {
		String name = "Davesprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getDavespriteQuote(int line) {
		String name = "Davesprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getDirkQuote() {
		String name = "Dirk";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getDirkQuote(int line) {
		String name = "Dirk";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getDocQuote() {
		String name = "Doc";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getDocQuote(int line) {
		String name = "Doc";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getDragonspriteQuote() {
		String name = "Dragonsprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getDragonspriteQuote(int line) {
		String name = "Dragonsprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getEquiusQuote() {
		String name = "Equius";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getEquiusQuote(int line) {
		String name = "Equius";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getEridanQuote() {
		String name = "Eridan";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getEridanQuote(int line) {
		String name = "Eridan";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getErisolspriteQuote() {
		String name = "Erisolsprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getErisolspriteQuote(int line) {
		String name = "Erisolsprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getFeferiQuote() {
		String name = "Feferi";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getFeferiQuote(int line) {
		String name = "Feferi";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getFefetaspriteQuote() {
		String name = "Fefetasprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getFefetaspriteQuote(int line) {
		String name = "Fefetasprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getGamzeeQuote() {
		String name = "Gamzee";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getGamzeeQuote(int line) {
		String name = "Gamzee";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getHICQuote() {
		String name = "HIC";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getHICQuote(int line) {
		String name = "HIC";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getHorussQuote() {
		String name = "Horuss";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getHorussQuote(int line) {
		String name = "Horuss";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getHussieQuote() {
		String name = "Hussie";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getHussieQuote(int line) {
		String name = "Hussie";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getJadeQuote() {
		String name = "Jade";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getJadeQuote(int line) {
		String name = "Jade";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getJadespriteQuote() {
		String name = "Jadesprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getJadespriteQuote(int line) {
		String name = "Jadesprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getJakeQuote() {
		String name = "Jake";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getJakeQuote(int line) {
		String name = "Jake";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getJaneQuote() {
		String name = "Jane";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getJaneQuote(int line) {
		String name = "Jane";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getJasperspriteQuote() {
		String name = "Jaspersprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getJasperspriteQuote(int line) {
		String name = "Jaspersprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getJohnQuote() {
		String name = "John";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getJohnQuote(int line) {
		String name = "John";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getKanayaQuote() {
		String name = "Kanaya";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getKanayaQuote(int line) {
		String name = "Kanaya";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getKankriQuote() {
		String name = "Kankri";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getKankriQuote(int line) {
		String name = "Kankri";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getKarkatQuote() {
		String name = "Karkat";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getKarkatQuote(int line) {
		String name = "Karkat";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getKurlozQuote() {
		String name = "Kurloz";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getKurlozQuote(int line) {
		String name = "Kurloz";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getLatulaQuote() {
		String name = "Latula";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getLatulaQuote(int line) {
		String name = "Latula";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getMeenahQuote() {
		String name = "Meenah";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getMeenahQuote(int line) {
		String name = "Meenah";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getMeulinQuote() {
		String name = "Meulin";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getMeulinQuote(int line) {
		String name = "Meulin";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getMitunaQuote() {
		String name = "Mituna";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getMitunaQuote(int line) {
		String name = "Mituna";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getNannaspriteQuote() {
		String name = "Nannasprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getNannaspriteQuote(int line) {
		String name = "Nannasprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getNepetaQuote() {
		String name = "Nepeta";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getNepetaQuote(int line) {
		String name = "Nepeta";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getPorrimQuote() {
		String name = "Porrim";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getPorrimQuote(int line) {
		String name = "Porrim";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getRoseQuote() {
		String name = "Rose";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getRoseQuote(int line) {
		String name = "Rose";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getRoxyQuote() {
		String name = "Roxy";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getRoxyQuote(int line) {
		String name = "Roxy";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getRufiohQuote() {
		String name = "Rufioh";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getRufiohQuote(int line) {
		String name = "Rufioh";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getSolluxQuote() {
		String name = "Sollux";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getSolluxQuote(int line) {
		String name = "Sollux";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getSquarewaveQuote() {
		String name = "Squarewave";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getSquarewaveQuote(int line) {
		String name = "Squarewave";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getTavrispriteQuote() {
		String name = "Tavrisprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getTavrispriteQuote(int line) {
		String name = "Tavrisprite";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getTavrosQuote() {
		String name = "Tavros";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getTavrosQuote(int line) {
		String name = "Tavros";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getTereziQuote() {
		String name = "Terezi";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getTereziQuote(int line) {
		String name = "Terezi";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}

	public String getVriskaQuote() {
		String name = "Vriska";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		rand = new Random();
		int numOfLines = FileHelper.countLines(FILE);
		int line = rand.nextInt(numOfLines) + 1;
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
	
	public String getVriskaQuote(int line) {
		String name = "Vriska";
		File FILE = new File("./files/quotes/" + name.toLowerCase() + "-quotes.txt");
		return Colors.TEAL + Colors.BOLD + "Quote #" + line + " - " + Colors.NORMAL + FileHelper.readLine(FILE, line);
	}
}
