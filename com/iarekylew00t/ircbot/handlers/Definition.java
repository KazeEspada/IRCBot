package com.iarekylew00t.ircbot.handlers;

public class Definition {
	private String word, definition, url;	
	
	public Definition(String word) {
		this.word = word;
		this.definition = "No results found for " + word;
		this.url = null;
	}
	
	public Definition(String word, String definition) {
		this.word = word;
		this.definition = definition;
		this.url = null;
	}
	
	public Definition(String word, String definition, String url) {
		this.word = word;
		this.definition = definition;
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getWord() {
		return word;
	}
	
	public String getDefinition() {
		return definition;
	}
}
