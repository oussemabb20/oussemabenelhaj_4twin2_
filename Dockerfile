FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


# To build the Docker image, use the following command:
# docker build -t my-java-app .
# To run the Docker container, use the following command:
# docker run -p 8080:8080 my-java-app