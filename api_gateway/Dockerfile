FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/api_gateway-0.0.1-SNAPSHOT.jar
COPY $JAR_FILE api_gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api_gateway-0.0.1-SNAPSHOT.jar"]