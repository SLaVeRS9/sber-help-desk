FROM eclipse-temurin:17
RUN mkdir /app
COPY /target/ticket-service-1.0.jar /app
ENTRYPOINT ["java", "-jar", "/app/ticket-service-1.0.jar"]