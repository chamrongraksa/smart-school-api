# Stage 1: Build the application using Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Grant execution permission to the maven wrapper
RUN chmod +x ./mvnw
# Build the application safely
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application in a lightweight Java 21 environment
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar
# Expose the port your Spring Boot app runs on
EXPOSE 8081
# The command Render will use to start the server
ENTRYPOINT ["java", "-jar", "app.jar"]