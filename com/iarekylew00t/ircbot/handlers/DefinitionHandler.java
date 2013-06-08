package com.iarekylew00t.ircbot.handlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DefinitionHandler {
	private String DICT_BASE = "http://dictionary.reference.com/browse/";
	private String UDICT_BASE = "http://www.urbandictionary.com/define.php?term=";
	
	public DefinitionHandler() {}
	
	public Definition getDefinition(String word) {
		String newWord = word.replace(" ", "+");
		String url = DICT_BASE + newWord;
		try {
			Document doc = Jsoup.connect(url).get();
			Element link = doc.getElementsByClass("body").first();
			String definition = link.text();
			return new Definition(word, definition, url);
		} catch (Exception e) {
			return new Definition(word);
		}
	}

	public Definition getUrbanDefinition(String word) {
		String newWord = word.replace(" ", "+");
		String url = UDICT_BASE + newWord;
		try {
			Document doc = Jsoup.connect(url).get();
			Element link = doc.getElementsByClass("definition").first();
			String definition = link.text();
			return new Definition(word, definition, url);
		} catch (Exception e) {
			return new Definition(word);
		}
	}
	
}
