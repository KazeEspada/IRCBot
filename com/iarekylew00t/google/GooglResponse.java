package com.iarekylew00t.google;

import org.json.JSONException;
import org.json.JSONObject;

public class GooglResponse {
	private JSONObject json;
	
	public GooglResponse(String response) throws GooglException {
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
            throw new GooglException("failed to parse JSON response: " + response);
		}
	}
	
	public String getShortUrl() throws GooglException {
		try {
			return json.getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
            throw new GooglException("failed to parse \"id\" (shortUrl) from JSON");
		}
	}
	
	public String getLongUrl() throws GooglException {
		try {
			return json.getString("longUrl");
		} catch (JSONException e) {
			e.printStackTrace();
            throw new GooglException("failed to parse \"longUrl\" from JSON");
		}
	}
}
