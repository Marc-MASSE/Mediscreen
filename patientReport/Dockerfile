FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/patientReport-0.0.1-SNAPSHOT.jar .

EXPOSE 8083

CMD ["java", "-jar", "target/patientReport-0.0.1-SNAPSHOT.jar"]