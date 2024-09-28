FROM maven:3-amazoncorretto-17 AS builder

WORKDIR /app/dmtp-model
COPY dmtp-model/src src
COPY dmtp-model/pom.xml .
RUN mvn clean install

WORKDIR /app/mock-mts
COPY mock-mts/src src
COPY mock-mts/pom.xml .
RUN mvn package

FROM amazoncorretto:17-alpine-jdk
COPY --from=builder /app/mock-mts/target/mock-mts-0.0.1-SNAPSHOT.jar /app.jar
COPY observability/agent/opentelemetry-javaagent.jar /agent/opentelemetry-javaagent.jar
COPY observability/agent/otel.properties /agent/otel.properties
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5011", "-javaagent:/agent/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/agent/otel.properties", "-jar", "/app.jar"]
