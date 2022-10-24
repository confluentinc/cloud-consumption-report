FROM openjdk:17-alpine
RUN apk add --no-cache freetype fontconfig ttf-liberation
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]