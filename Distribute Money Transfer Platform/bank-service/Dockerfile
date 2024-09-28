FROM maven:3-amazoncorretto-17 AS builder
WORKDIR /app/bank-service
COPY bank-service/src src
COPY bank-service/pom.xml pom.xml
RUN mvn clean package

FROM amazoncorretto:17-alpine-jdk
COPY --from=builder /app/bank-service/target/bank-service-0.0.1-SNAPSHOT.jar /app.jar
COPY dmtp/observability/agent/opentelemetry-javaagent.jar /agent/opentelemetry-javaagent.jar
COPY dmtp/observability/agent/otel.properties /agent/otel.properties
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-javaagent:/agent/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/agent/otel.properties", "-jar", "/app.jar"]