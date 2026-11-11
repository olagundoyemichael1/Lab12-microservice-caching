FROM eclipse-temurin:25-jre
WORKDIR /app
COPY build/libs/microservice-caching-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
