FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/photo-app-users-api-1.0.0.jar photo-app-users-api.jar
ENTRYPOINT ["java", "-Djava.security/edg=file:/dev/./urandom", "-jar", "photo-app-users-api.jar"]