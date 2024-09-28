FROM maven:3-amazoncorretto-17 AS builder

WORKDIR /app/spring-boot-admin
COPY spring-boot-admin/src src
COPY spring-boot-admin/pom.xml .
RUN mvn clean package

FROM amazoncorretto:17-alpine-jdk
COPY --from=builder /app/spring-boot-admin/target/spring-boot-admin-0.0.1-SNAPSHOT.jar /app.jar
COPY observability/agent/opentelemetry-javaagent.jar /agent/opentelemetry-javaagent.jar
COPY observability/agent/otel.properties /agent/otel.properties
ENTRYPOINT ["java", "-javaagent:/agent/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/agent/otel.properties", "-jar", "/app.jar"]
