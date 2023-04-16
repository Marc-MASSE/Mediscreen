FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/patientInfo-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

CMD ["java", "-jar", "target/patientInfo-0.0.1-SNAPSHOT.jar"]