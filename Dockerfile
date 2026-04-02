FROM eclipse-temurin:21-jdk-alpine AS builder
COPY . /
RUN /gradlew clean bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/libs/user-management-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", \
  "-Xms256m", \
  "-Xmx1g", \
  "-XX:+PrintGCDetails", \
  "-XX:-UseContainerSupport", \
  "-jar", "app.jar"]

