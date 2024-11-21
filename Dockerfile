FROM openjdk:21-jdk-slim
LABEL authors="uzay"
COPY target/urun-0.0.1-SNAPSHOT.jar urun-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","urun-0.0.1-SNAPSHOT.jar"]