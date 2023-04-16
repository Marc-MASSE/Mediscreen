FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/patientNote-0.0.1-SNAPSHOT.jar .

EXPOSE 8082

CMD ["java", "-jar", "target/patientNote-0.0.1-SNAPSHOT.jar"]