FROM maven:3.9.5-eclipse-temurin-21
LABEL author jaspereap
ARG APP_DIR=/app
WORKDIR ${APP_DIR}
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
RUN mvn package -Dmaven.test.skip=true

ENV PORT=3000
ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PORT=6379
ENV SPRING_REDIS_DATABASE=0
ENV SPRING_REDIS_USERNAME=
ENV SPRING_REDIS_PASSWORD=

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/news-0.0.1-SNAPSHOT.jar