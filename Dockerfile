FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM openjdk:11

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/transaction-0.0.1-SNAPSHOT.jar /app/transaction-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/transaction-0.0.1-SNAPSHOT.jar" ]