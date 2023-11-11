FROM openjdk:17-jdk-slim
RUN mkdir backend
ARG JAR_FILE_PATH=build/libs/tp-back-internal-app-1.0.jar
COPY ${JAR_FILE_PATH} /backend/tp-back-internal-app-1.0.jar
ENTRYPOINT ["java", "-jar", "backend/tp-back-internal-app-1.0.jar"]
