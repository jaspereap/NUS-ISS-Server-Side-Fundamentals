## Dockerfile Guide

# Starting with this Linux server
FROM maven:3-eclipse-temurin-21

## Building the application
# Create a directory call /app
# go into directory cd /app
WORKDIR /app

# everything after this is in /app
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
# copy directory
COPY .mvn .mvn
COPY src src

# Build the application
RUN mvn package -Dmaven.test.skip=true
## Build Complete

## Run the application
# Define environemnt variable
# Cloud-providers tell us what port to run on, using PORT
ENV PORT=8080

# Expose the port
EXPOSE ${PORT}

# Run the program
# Set port then run
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day13work-0.0.1-SNAPSHOT.jar

## Docker build
# docker build -t jaspereap/day13:v1.0.0 .