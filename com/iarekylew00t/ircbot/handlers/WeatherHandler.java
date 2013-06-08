package com.iarekylew00t.ircbot.handlers;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeatherHandler {
	private String UNITS = "f";

	public WeatherHandler() {}
	public WeatherHandler(String unit) {
		if (!unit.equalsIgnoreCase("f") || !unit.equalsIgnoreCase("c"))
			throw new IllegalArgumentException("ERROR: Can only accept \"f\" or \"c\"");
		this.UNITS = unit.toLowerCase();
	}
	
	public Weather getForecast(String zipcode) throws Exception {
		String loc = getLocation(zipcode);
		String cond = getCondition(zipcode);
		int temp = getTemperature(zipcode);
		int flik = getFeelsLikeTemp(zipcode);
		return new Weather(loc, temp, flik, cond, UNITS);
	}
	
	public void setUnits(String unit) {
		if (!unit.equals("f") || !unit.equals("c"))
			throw new IllegalArgumentException("ERROR: Can only accept \"f\" or \"c\"");
		this.UNITS = unit;
	}
	
	public String getLocation(String zipcode) throws Exception {
        URL weatherXml = new URL("http://xml.weather.com/weather/local/" + zipcode + "?cc=&unit=" + UNITS);
        InputStream xml = weatherXml.openStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("loc");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                return eElement.getElementsByTagName("dnam").item(0).getTextContent();
            }
		return null;
	}

	public int getTemperature(String zipcode) throws Exception{
        URL weatherXml = new URL("http://xml.weather.com/weather/local/" + zipcode + "?cc=&unit=" + UNITS);
        InputStream xml = weatherXml.openStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("cc");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                return Integer.parseInt(eElement.getElementsByTagName("tmp").item(0).getTextContent());
            }
		return -1;
	}

	public int getFeelsLikeTemp(String zipcode) throws Exception {
        URL weatherXml = new URL("http://xml.weather.com/weather/local/" + zipcode + "?cc=&unit=" + UNITS);
        InputStream xml = weatherXml.openStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("cc");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                return Integer.parseInt(eElement.getElementsByTagName("flik").item(0).getTextContent());
            }
		return -1;
	}
	
	public String getCondition(String zipcode) throws Exception {
        URL weatherXml = new URL("http://xml.weather.com/weather/local/" + zipcode + "?cc=&unit=" + UNITS);
        InputStream xml = weatherXml.openStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("cc");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                return eElement.getElementsByTagName("t").item(0).getTextContent();
            }
		return null;
	}
}
