#Docker Base image : Alpine linux with java 8
FROM openjdk:8-jre-alpine

RUN ["java", "-version"]

# Add curl command to verify any api connections
RUN apk add --no-cache curl

#Copy the jar file to Workdir

ADD ./target/services.jar  /opt

#Port the container listens on

EXPOSE 8081

#Command to execute when docker is run.

ENTRYPOINT ["java", "-jar", "/opt/services.jar"]
