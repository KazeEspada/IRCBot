@ECHO OFF
javac -classpath ./lib/pircbot.jar;./lib/javax.mail.jar;./lib/json.jar;./lib/guava.jar;./lib/chatterbot.jar ./com/iarekylew00t/email/*.java ./com/iarekylew00t/ircbot/*.java ./com/iarekylew00t/google/*.java ./com/iarekylew00t/chatbot/*.java
PAUSE