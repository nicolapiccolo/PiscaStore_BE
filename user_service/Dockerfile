FROM openjdk:13-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user_service.jar
ENTRYPOINT ["java","-jar","/user_service.jar"]