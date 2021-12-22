FROM openjdk:13-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} catalog_service.jar
ENTRYPOINT ["java","-jar","/catalog_service.jar"]