FROM openjdk:19
WORKDIR usr/src/app
ARG JAR_FILE=target/iis-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN ["mvn", "-DskipTests", "clean", "package"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]