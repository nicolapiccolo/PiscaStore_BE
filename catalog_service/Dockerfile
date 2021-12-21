FROM openjdk:13-alpine
RUN addgroup -S spring && adduser -S spring -G spring
VOLUME /tmp
EXPOSE 8082
ARG DEPENDENCY=target
ADD ${DEPENDENCY}/*.jar catalog_service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/catalog_service.jar"]