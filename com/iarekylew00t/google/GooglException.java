package com.iarekylew00t.google;

public class GooglException extends Exception {

    private static final long serialVersionUID = 1L;

    public GooglException(StringBuffer response) {
            super(response == null ? "null" : response.toString());
    }

    public GooglException(String response) {
            super(response);
    }

}