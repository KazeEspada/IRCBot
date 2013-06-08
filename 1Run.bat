@ECHO OFF
java -Dfile.encoding=UTF8 -d64 -server -classpath .;./lib/pircbotx-1.9.jar;./lib/javax.mail.jar;./lib/json.jar;./lib/jsoup.jar;./lib/chatbot.jar;./lib/apache-commons.jar com.iarekylew00t.ircbot.IRCBotMain -XX:UseParallelOldGC -Xms1024M - Xmx1024M -XX:NewRatio=3 
pause