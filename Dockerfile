# Use JDK 21 as the base image
FROM openjdk:21-jdk
 
# Set working directory
WORKDIR /app

# Copy the JAR file and rename it to user-profile-service.jar
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-service.jar

# Expose port
EXPOSE 8090

# Run the application
ENTRYPOINT ["java", "-jar", "api-gateway-service.jar"]
