FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/patientui-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "target/patientui-0.0.1-SNAPSHOT.jar"]