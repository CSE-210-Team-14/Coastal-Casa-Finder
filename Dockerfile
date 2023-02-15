FROM openjdk:8-jdk-alpine

RUN apk update && \
    apk upgrade && \
    apk add --no-cache bash && \
    apk add --no-cache curl

WORKDIR /app

COPY ./target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
