FROM maven:3-amazoncorretto-17 AS builder

WORKDIR /app/dmtp-config
COPY dmtp-config/config-server/pom.xml config-server/pom.xml
COPY dmtp-config/config-server/src config-server/src
COPY dmtp-config/config-repo ./config-repo

WORKDIR /app/dmtp-config/config-server
RUN mvn clean package

FROM amazoncorretto:17-alpine-jdk
RUN apk add curl

COPY --from=builder /app/dmtp-config/config-server/target/config-server-0.0.1-SNAPSHOT.jar /app.jar
COPY observability/agent/opentelemetry-javaagent.jar /agent/opentelemetry-javaagent.jar
COPY observability/agent/otel.properties /agent/otel.properties
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5004", "-javaagent:/agent/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/agent/otel.properties", "-jar", "/app.jar"]
