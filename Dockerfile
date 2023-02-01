FROM eclipse-temurin:19
WORKDIR usr/src/app
ARG JAR_FILE=target/iis-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]