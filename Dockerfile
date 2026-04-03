# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-21 AS build
WORKDIR /app
COPY . .
# Grant execution permission to the maven wrapper
RUN chmod +x ./mvnw
# Build the application safely
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application in a lightweight Java environment
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar
# Expose the port your Spring Boot app runs on
EXPOSE 8081
# The command Render will use to start the server
ENTRYPOINT ["java", "-jar", "app.jar"]