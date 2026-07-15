
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

COPY gradle/ gradle/
COPY gradlew gradlew
COPY build.gradle settings.gradle /app/

RUN chmod +x gradlew

COPY src/ src/

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
