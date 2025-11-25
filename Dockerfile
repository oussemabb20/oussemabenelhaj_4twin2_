FROM eclipse-temurin:17-jre
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
































# This Dockerfile uses the Eclipse Temurin JRE 17 base image to run a Java application.

# It sets the working directory to /app, copies the built JAR file from the target directory into the container,
# exposes port 8080, and specifies the command to run the application using the java -jar command.
# The application JAR file is assumed to be located in the target directory after building the project.
# The Dockerfile is designed to create a lightweight container for running Java applications efficiently.
# The use of Eclipse Temurin ensures that the container has a reliable and up-to-date Java runtime environment.
# The Dockerfile is structured to facilitate easy deployment of Java applications in a containerized environment.










