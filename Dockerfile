FROM openjdk:17
LABEL authors="narinderpalsingh"
WORKDIR /app
ADD target/jwt-implementation-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "/app/jwt-implementation-0.0.1-SNAPSHOT.jar"]