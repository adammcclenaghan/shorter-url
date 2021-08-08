# Build
FROM maven:3.6.1-jdk-8-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package
FROM openjdk:8-jdk-alpine

COPY --from=build /home/app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
