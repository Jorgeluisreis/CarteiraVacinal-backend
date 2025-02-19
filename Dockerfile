FROM openjdk:21-jdk-slim AS builder
WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/carteiravacinal-backend-1.0.jar app.jar

EXPOSE 3300

CMD ["java", "-jar", "app.jar"]