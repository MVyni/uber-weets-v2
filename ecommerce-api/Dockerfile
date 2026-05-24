FROM ubuntu:latest AS build

# Build stage
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN app-get install maven -y
RUN mvn clean install

# Runtime stage
FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /target/ecommerce-api-0.0.1-SNAPSHOT.jar ecommerce-api.jar

ENTRYPOINT ["java", "-jar", "ecommerce-api.jar"]