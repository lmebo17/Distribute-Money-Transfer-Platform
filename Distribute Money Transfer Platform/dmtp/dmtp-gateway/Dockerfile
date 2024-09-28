FROM maven:3-amazoncorretto-17 AS builder
WORKDIR /app/dmtp-gateway
COPY dmtp-gateway/src src
COPY dmtp-gateway/pom.xml .
RUN mvn clean package

FROM amazoncorretto:17-alpine-jdk
COPY --from=builder /app/dmtp-gateway/target/dmtp-gateway-0.0.1-SNAPSHOT.jar /app.jar
COPY observability/agent/opentelemetry-javaagent.jar /agent/opentelemetry-javaagent.jar
COPY observability/agent/otel.properties /agent/otel.properties
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5009", "-javaagent:/agent/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/agent/otel.properties", "-jar", "/app.jar"]
