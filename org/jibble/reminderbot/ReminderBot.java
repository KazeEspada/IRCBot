/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of ReminderBot.

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ReminderBot.java,v 1.3 2004/05/29 19:44:30 pjm2 Exp $

*/

package org.jibble.reminderbot;

import org.jibble.pircbot.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;

public class ReminderBot extends PircBot implements Runnable {

    private static final String REMINDER_FILE = "reminders.dat";
    private static final String SONG_LIST = "songs.txt";
    private boolean req = false;
    
    public ReminderBot(String name) {
        loadReminders();
        setName(name);
        setAutoNickChange(true);
        dispatchThread = new Thread(this);
        dispatchThread.start();
    }
    
    @SuppressWarnings("unchecked")
	public synchronized void onMessage(String channel, String sender, String login, String hostname, String message) {

        Pattern messagePattern = Pattern.compile("^\\s*(?i:(" + getNick() + ")?\\s*[\\:,]?\\s*remind\\s+me\\s+in\\s+(((\\d+\\.?\\d*|\\.\\d+)\\s+(weeks?|days?|hours?|hrs?|minutes?|mins?|seconds?|secs?)[\\s,]*(and)?\\s+)+)(.*)\\s*)$");
        Matcher m = messagePattern.matcher(message);
        if (m.matches()) {
        String reminderMessage = m.group(7);
            String periods = m.group(2);
            
            long set = System.currentTimeMillis();
            long due = set;
            
            try {
                double weeks = getPeriod(periods, "weeks|week");
                double days = getPeriod(periods, "days|day");
                double hours = getPeriod(periods, "hours|hrs|hour|hr");
                double minutes = getPeriod(periods, "minutes|mins|minute|min");
                double seconds = getPeriod(periods, "seconds|secs|second|sec");
                due += (weeks * 604800 + days * 86400 + hours * 3600 + minutes * 60 + seconds) * 1000;
            }
            catch (NumberFormatException e) {
                sendMessage(channel, "i cant deal with numbers like that " + sender);
                return;
            }
            
            if (due == set) {
                sendMessage(channel, "Example of correct usage: \"Remind me in 1 hour, 10 minutes to check the oven.\"  I understand all combinations of weeks, days, hours, minutes and seconds.");
                return;
            }
            Reminder reminder = new Reminder(channel, sender, reminderMessage, set, due);
            sendMessage(channel, "0kay " + sender + " ill remind y0u ab0ut that 0n " + new Date(reminder.getDueTime()));
            reminders.add(reminder);
            dispatchThread.interrupt();
        } else if (message.equalsIgnoreCase("$time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": the time is " + time);
        } else if (message.equalsIgnoreCase("$boner")) {
            sendMessage(channel, sender + ": b0ner");
        } else if (message.equalsIgnoreCase("$req")) {
            if (req == true)
                sendMessage(channel, "requests are currently 0pen; please use: $req <s0ngname>");
            else
                sendMessage(channel, "requests are currently cl0sed");
        } else if (message.equalsIgnoreCase("$reqon")) {
            if (sender.equals("IAreKyleW00t")) {
                req = true;
                sendMessage("#hs_admin", "requests have been turn 0n by " + sender);
                sendMessage("#hs_radio", "requests have been turn 0n by " + sender);
                sendMessage("#hs_rp", "requests have been turn 0n by " + sender);
                sendMessage("#hs_nsfw", "requests have been turn 0n by " + sender);
            } else {
                sendMessage(channel, "im n0t all0wed t0 let y0u d0 that " + sender);
            }
        } else if (message.equalsIgnoreCase("$reqoff")) {
            if (sender.equals("IAreKyleW00t")) {
                req = false;
                sendMessage("#hs_admin", "requests have been turn 0ff by " + sender);
                sendMessage("#hs_radio", "requests have been turn 0ff by " + sender);
                sendMessage("#hs_rp", "requests have been turn 0ff by " + sender);
                sendMessage("#hs_nsfw", "requests have been turn 0ff by " + sender);
            } else {
                sendMessage(channel, "im n0t all0wed t0 let y0u d0 that " + sender);
            }
        } else if (message.equalsIgnoreCase("$commands")) {
            sendMessage(channel, "boner, commands, faq, gearup, kill, marco, mspa, mspawiki, ping, radio, req, reqoff, reqon, revive, serve, shoot, slap, slay, stab, time, wiki");
        } else if (message.equalsIgnoreCase("$gearup")) {
            sendMessage(channel, "y0u are n0w geared up " + sender);
        } else if (message.equalsIgnoreCase("$ping")) {
            sendMessage(channel, sender + ": p0ng");
        } else if (message.equalsIgnoreCase("$marco")) {
            sendMessage(channel, sender + ": p0l0");
        } else if (message.equalsIgnoreCase("$mspa")) {
            sendMessage(channel, sender + ": http://www.mspaintadventures.com/");
        } else if (message.equalsIgnoreCase("$radio")) {
            sendMessage(channel, sender + ": http://mixlr.com/iarekylew00t/");
        } else if (message.equalsIgnoreCase("$mspawiki")) {
            sendMessage(channel, sender + ": http://mspaintadventures.wikia.com/");
        } else if (message.equalsIgnoreCase("$wiki")) {
            sendMessage(channel, sender + ": http://wikipedia.org/");
        } else if (message.equalsIgnoreCase("$tumblr")) {
            sendMessage(channel, "i need a name " + sender);
        } else if (message.equalsIgnoreCase("$faq")) {
            sendMessage(channel, sender + ": http://goo.gl/53qWN/");
        } else if (message.equalsIgnoreCase("$kill")) {
            sendMessage(channel, "i need a name " + sender);
        } else if (message.equalsIgnoreCase("$shoot")) {
            sendMessage(channel, "i need a name " + sender);
        } else if (message.equalsIgnoreCase("$revive")) {
            sendMessage(channel, "i need a name " + sender);
        } else if (message.equalsIgnoreCase("$slay")) {
            sendMessage(channel, "i need a name " + sender);
        } else if (message.equalsIgnoreCase("$stab")) {
            sendMessage(channel, "i need a name " + sender);
        } else if (message.equalsIgnoreCase("$slap")) {
            sendMessage(channel, "i need a name " + sender);
        } else if (message.equalsIgnoreCase("$serve")) {
            sendMessage(channel, "i cann0t just serve n0thing " + sender);
        } else if (message.startsWith("$kill ")) {
            String input = message.substring(6);
            if (input.equalsIgnoreCase("aradiabot") || input.equalsIgnoreCase("self")) {
                sendMessage(channel, "why w0uld i kill myself when im already dead " + sender);
            } else if (input.equalsIgnoreCase("iarekylew00t")) {
                sendMessage(channel, "d0 y0u want to end the stati0n " + sender);
            } else if (input.equals("")) {
                sendMessage(channel, "i need a name " + sender);
            } else {
              sendMessage(channel, "killing " + input);
            }
        } else if(message.startsWith("$revive ")) {
            String input = message.substring(8);
            sendMessage(channel, "reviving " + input);
        } else if(message.startsWith("$slay ")) {
            String input = message.substring(6);
            if (input.equalsIgnoreCase("aradiabot") || input.equalsIgnoreCase("self")) {
                sendMessage(channel, "i cann0t d0 that " + sender);
            } else if (input.equalsIgnoreCase("iarekylew00t")) {
                sendMessage(channel, "seri0sly " + sender);
            } else if (input.equals("")) {
                sendMessage(channel, "i need a name " + sender);
            } else {
            sendMessage(channel, input + " has been slain");
            }
        } else if (message.startsWith("$shoot ")) {
            String input = message.substring(7);
            if (input.equalsIgnoreCase("aradiabot") || input.equalsIgnoreCase("self")) {
                sendMessage(channel, "that w0uld be very stupid 0f me t0 d0 " + sender);
            } else if (input.equals("")) {
                sendMessage(channel, "i need a name " + sender);
            } else {
                sendMessage(channel, "sh00ting " + input);
            }
        } else if (message.startsWith("$serve ")) {
            String input = message.substring(7);
            if (input.equals("")){
                sendMessage(channel, "i cann0t just serve n0thing");
            } else {
                sendMessage(channel, "serving " + input + " f0r everyone");
            }
        } else if (message.startsWith("$mspawiki ")) {
            String input = message.substring(10);
            if (input.equals("")){
                sendMessage(channel, sender + ": http://mspaintadventures.wikia.com/");
            } else {
                String newInput = input.replace(' ','_');
                sendMessage(channel, "here are y0ur search results " + sender + ": http://mspaintadventures.wikia.com/wiki/index.php?search=" + newInput);
            }
        } else if (message.startsWith("$wiki ")) {
            String input = message.substring(6);
            if (input.equals("")){
                sendMessage(channel, sender + ": http://wikipedia.org/");
            } else {
                String newInput = input.replace(' ','_');
                sendMessage(channel, "here are y0ur search results " + sender + ": http://en.wikipedia.org/wiki/" + newInput);
            }
        } else if (message.startsWith("$tumblr ")) {
            String input = message.substring(8);
            if (input.equals("")){
                sendMessage(channel, "i need a name " + sender);
            } else {
                sendMessage(channel, sender + ": " + input + ".tumblr.com");
            }
        } else if (message.startsWith("$stab ")) {
            String input = message.substring(6);
            if (input.equals("")){
                sendMessage(channel, "i need a name " + sender);
            } else {
                sendMessage(channel, input + " has been stabbed");
            }
        } else if (message.startsWith("$slap ")) {
            String input = message.substring(6);
            if (input.equals("")){
                sendMessage(channel, "i need a name " + sender);
            } else {
                sendMessage(channel, "slapping " + input);
            }
        } else if (message.startsWith("$req ")) {
            String input = message.substring(5);
            if (req == true) {
                if (input.equals("")){
                    sendMessage(channel, "please specify a s0ng name " + sender);
                } else {
                    sendMessage(channel, "y0ur s0ng request has been added t0 the list " + sender);
                    try {
                        Writer output = new BufferedWriter(new FileWriter(SONG_LIST, true));
                        output.append(input + "\n");
                        output.close();
                    }
                    catch (Exception e) {
                        // If it doesn't work, no great loss!
                    }
                }
            } else {
                sendMessage(channel, "requests are currently cl0sed " + sender);
            }
        }
    }
    
    public double getPeriod(String periods, String regex) throws NumberFormatException {
        Pattern pattern = Pattern.compile("^.*?([\\d\\.]+)\\s*(?i:(" + regex + ")).*$");
        Matcher m = pattern.matcher(periods);
        m = pattern.matcher(periods);
        if (m.matches()) {
            double d = Double.parseDouble(m.group(1));
            if (d < 0 || d > 1e6) {
                throw new NumberFormatException("Number too large or negative (" + d + ")");
            }
            return d;
        }
        return 0;
    }
    
    @SuppressWarnings("unchecked")
	public synchronized void run() {
        boolean running = true;
        while (running) {

            // If the list is empty, wait until something gets added.
            if (reminders.size() == 0) {
                try {
                    wait();
                }
                catch (InterruptedException e) {
                    // Do nothing.
                }
            }

            Reminder reminder = (Reminder) reminders.getFirst();
            long delay = reminder.getDueTime() - System.currentTimeMillis();
            if (delay > 0) {
                try {
                    wait(delay);
                }
                catch (InterruptedException e) {
                    // A new Reminder was added. Sort the list.
                    Collections.sort(reminders);
                    saveReminders();
                }
            }
            else {
                sendMessage(reminder.getChannel(), "hell0 " + reminder.getNick() + " y0u asked me t0 remind y0u " + reminder.getMessage());
                reminders.removeFirst();
                saveReminders();
            }
            
        }
    }
    
    private void saveReminders() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(REMINDER_FILE)));
            out.writeObject(reminders);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            // If it doesn't work, no great loss!
        }
    }
    
    @SuppressWarnings("rawtypes")
	private void loadReminders() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(REMINDER_FILE)));
            reminders = (LinkedList) in.readObject();
            in.close();
        }
        catch (Exception e) {
            // If it doesn't work, no great loss!
        }
    }    
    
    private Thread dispatchThread;
    @SuppressWarnings("rawtypes")
	private LinkedList reminders = new LinkedList();
    
}