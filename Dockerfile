FROM amazoncorretto:17-alpine-jdk
LABEL maintainer=hos
COPY taget/app-0.0.1-SNAPSHOT.jar hos.app.jar
ENTRYPOINT	["java","-jar","/hos.app.jar"]