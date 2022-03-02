FROM openjdk:13-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} order_service.jar
ENTRYPOINT ["java","-jar","/order_service.jar"]