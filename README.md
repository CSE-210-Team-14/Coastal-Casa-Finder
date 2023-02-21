# Coastal-Casa-Finder

## Required Environment
JDK 1.8 (JAVA 8) and Maven

## Steps

1. change spring.profiles.active=prod to spring.profiles.active=dev in application.properties file, and DO NOT commit this file. 
2. fill in your local MySQL server's username and password in application-dev.properties

## Database Initialization
We use MySQL 5.7 in production.   
Run createDB.sh (under resources/scripts), be sure to change the MySQL server's username and password first. 