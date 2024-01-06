FROM maven:3.9.5-eclipse-temurin-21

WORKDIR /app

COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

RUN mvn package -Dmaven.test.skip=true

ENV PORT=3000
ENV INSTANCE_NAME="dov bear"
ENV INSTANCE_HASH="abc123"

EXPOSE ${PORT}

ENTRYPOINT java -jar target/dovbear-0.0.1-SNAPSHOT.jar