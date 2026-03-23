# Use the official Eclipse Temurin JDK 25 image
FROM eclipse-temurin:25-jdk-alpine AS builder

RUN apk add --no-cache maven

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]