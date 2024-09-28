FROM maven:3-amazoncorretto-17 AS transfer-repository-builder

WORKDIR /app/dmtp-model
COPY dmtp-model/src src
COPY dmtp-model/pom.xml .
RUN mvn clean install

WORKDIR /app/transfer-repository
COPY transfer-repository/src src
COPY transfer-repository/pom.xml .
RUN mvn package


FROM amazoncorretto:17-alpine-jdk
COPY --from=transfer-repository-builder /app/transfer-repository/target/transfer-repository-0.0.1-SNAPSHOT.jar /app.jar
COPY observability/agent/opentelemetry-javaagent.jar /agent/opentelemetry-javaagent.jar
COPY observability/agent/otel.properties /agent/otel.properties
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5010", "-javaagent:/agent/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/agent/otel.properties", "-jar", "/app.jar"]
