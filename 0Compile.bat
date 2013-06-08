@ECHO OFF
javac -classpath ./lib/pircbotx-1.9.jar;./lib/javax.mail.jar;./lib/json.jar;./lib/jsoup.jar;./lib/chatbot.jar;./lib/apache-commons.jar ./com/iarekylew00t/ircbot/*.java ./com/iarekylew00t/ircbot/handlers/*.java ./com/iarekylew00t/ircbot/listeners/*.java ./com/iarekylew00t/email/*.java ./com/iarekylew00t/google/*.java ./com/iarekylew00t/helpers/*.java ./com/iarekylew00t/chatbot/*.java ./com/iarekylew00t/managers/*.java ./com/iarekylew00t/encryption/*.java
ECHO ----- ALL FILES COMPILED SUCCESSFULLY -----
PAUSE