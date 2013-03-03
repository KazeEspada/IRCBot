package com.iarekylew00t.ircbot;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class Reminder implements Serializable, Comparable {
	private static final long serialVersionUID = 8291125258275118910L;
	public Reminder(String channel, String nick, String message, long setTime, long dueTime) {
        this.channel = channel;
        this.nick = nick;
        this.message = message;
        this.setTime = setTime;
        this.dueTime = dueTime;
    }
    
    public String getChannel() {
        return channel;
    }
    
    public String getNick() {
        return nick;
    }
    
    public String getMessage() {
        return message;
    }
    
    public long getSetTime() {
        return setTime;
    }

    public long getDueTime() {
        return dueTime;
    }
    
    public int compareTo(Object o) {
        if (o instanceof Reminder) {
            Reminder other = (Reminder) o;
            if (dueTime < other.dueTime) {
                return -1;
            }
            else if (dueTime > other.dueTime) {
                return 1;
            }
        }
        return 0;
    }
    
    private String channel;
    private String nick;
    private String message;
    private long setTime;
    private long dueTime;
    
}