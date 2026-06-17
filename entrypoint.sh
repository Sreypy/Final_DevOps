#!/bin/bash

# Start SSH service
service ssh start

# Start NGINX
service nginx start

# Build the Spring Boot app
cd /app
mvn clean package -DskipTests

# Run the Spring Boot app
java -jar target/*.jar