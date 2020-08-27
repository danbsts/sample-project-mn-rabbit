FROM gradle:6.6-jdk8 AS build
COPY build.gradle gradle.properties gradlew ./
COPY gradle ./gradle
RUN ./gradlew -i --no-daemon
COPY . .
RUN ./gradlew assemble --no-daemon

FROM openjdk:8-jre-slim
COPY --from=build /home/gradle/build/libs/sample-project-mn-rabbit-*-all.jar sample-project-mn-rabbit.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "sample-project-mn-rabbit.jar"]