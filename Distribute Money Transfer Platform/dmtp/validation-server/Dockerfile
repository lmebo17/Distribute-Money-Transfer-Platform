FROM maven:3-amazoncorretto-17 AS builder

WORKDIR /app/dmtp-model
COPY dmtp-model/src src
COPY dmtp-model/pom.xml .
RUN mvn clean install

WORKDIR /app/validation-server
COPY validation-server/src src
COPY validation-server/pom.xml .
RUN mvn package

FROM amazoncorretto:17-alpine-jdk
COPY --from=builder /app/validation-server/target/validation-server-0.0.1-SNAPSHOT.jar /app.jar
COPY observability/agent/opentelemetry-javaagent.jar /agent/opentelemetry-javaagent.jar
COPY observability/agent/otel.properties /agent/otel.properties
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5007", "-javaagent:/agent/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/agent/otel.properties", "-jar", "/app.jar"]
