FROM amazoncorretto:17-alpine-jdk
COPY target/app-0.0.1-SNAPSHOT.jar hos.app.jar
ENTRYPOINT	["java","-jar","/hos.app.jar"]
