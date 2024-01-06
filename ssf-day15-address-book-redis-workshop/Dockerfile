FROM maven:3.9.5-eclipse-temurin-21

WORKDIR /app
# everything after this is in /app
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
RUN mvn package -Dmaven.test.skip=true

ENV PORT=3000

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/simple-address-book-0.0.1-SNAPSHOT.jar
