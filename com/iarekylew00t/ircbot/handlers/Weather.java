package com.iarekylew00t.ircbot.handlers;

public class Weather {
	private String location, condition, units;
	private int temperature, feelsLike;
	
	public Weather(String loc, int temp, int flik, String cond, String units) {
		this.location = loc;
		this.condition = cond;
		this.temperature = temp;
		this.feelsLike = flik;
		this.units = units.toUpperCase();
	}
	
	public String getTempUnits() {
		return temperature + units;
	}
	
	public String getFeelsTempUnits() {
		return feelsLike + units;
	}
	
	public String getUnits() {
		return units;
	}
	
	public int getTemp() {
		return temperature;
	}
	
	public int getFeelsLikeTemp() {
		return feelsLike;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getCondition() {
		return condition;
	}
}
