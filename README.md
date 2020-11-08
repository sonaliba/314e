# 314e Hackathon to hire

Problem Statement

Write an app/program to scan through a given webpage, and display the top 10 frequent words and the top 10 frequent word pairs (two words in the same order) along with their frequency. In case the webpage contains hyperlinks, these hyperlinked urls need to be expanded and the words on these pages also should be scanned to come up with the frequent words. 

Important points to note:

You will have to assume that the maximum number of levels you have to expand for urls within a url as 4
Ignore any external url links


Build

javac -cp "path to jar of Jsoup" Parser.java

Run

java Parser url

example- java Parser https://www.314e.com

OR



Build and Run in Eclipse-

Download and import project in Eclipse

Set url in arguments in Run Configurations

Run Project



Version of java used

java version "1.8.0_251"



