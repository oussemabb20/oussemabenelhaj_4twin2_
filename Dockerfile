# Use only runtime stage (JAR already built by Jenkins)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the pre-built JAR from Jenkins workspace
COPY target/*.jar app.jar

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring && \
    chown spring:spring app.jar

USER spring:spring

# Expose port
EXPOSE 8080

# Run the application with optimized JVM settings
ENTRYPOINT ["java", \
    "-Xms256m", \
    "-Xmx512m", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", \
    "app.jar"]