FROM openjdk:17

ADD target/shortener-0.0.1-SNAPSHOT.jar shortener.jar

EXPOSE 8080

CMD ["java","-jar","shortener.jar"]