# Coastal-Casa-Finder

## Required Environment
JDK 1.8 (JAVA 8), Maven, and MySQL

## Documentation
After running the application, you can find the documentation here: [SwaggerUi](http://localhost:8080/swagger-ui.html#/)

## Steps

1. change spring.profiles.active=prod to spring.profiles.active=dev in application.properties file, and DO NOT commit this file. 
2. fill in your local MySQL server's username and password in application-dev.properties

## Database Initialization
We use MySQL 5.7 in production.   
Run createDB.sh (under resources/scripts), be sure to change the MySQL server's username and password first. 

## Project Architecture
![arch](https://user-images.githubusercontent.com/52941906/225173167-3fe15b51-1d63-4569-aa0f-72b9392b7ec6.png)


