FROM java:8-jdk-alpine
RUN mkdir /usr/app
COPY ./target/pocketCare-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pocketCare-0.0.1-SNAPSHOT.jar"]