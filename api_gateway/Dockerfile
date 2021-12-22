FROM openjdk:13-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api_gateway.jar
ENTRYPOINT ["java","-jar","/api_gateway.jar"]