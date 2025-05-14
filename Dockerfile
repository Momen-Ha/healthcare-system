# ---- build stage ----
FROM maven:3.9.7-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn -q package -DskipTests

# ---- runtime stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/healthcare-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-XX:+UseG1GC","-jar","/app/app.jar"]
