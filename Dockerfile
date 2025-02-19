FROM maven:3.8.6-openjdk-21 AS builder
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/carteiravacinal-backend-1.0.jar app.jar

EXPOSE 3300

CMD ["java", "-jar", "app.jar"]