FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY cert/apiCipherKey.jks apiCipherKey.jks
COPY jce-jdk8/* /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
COPY target/photo-app-config-server-1.0.0.jar photo-app-config-server.jar
ENTRYPOINT ["java", "-Djava.security/edg=file:/dev/./urandom", "-jar", "ConfigServer.jar"]