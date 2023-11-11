FROM openjdk:17-jdk-slim
RUN mkdir backend
ADD ./build/libs/tp-back-internal-app-1.0.jar ./backend/tp-back-internal-app-1.0.jar
ENTRYPOINT ["java", "-jar", "backend/tp-back-internal-app-1.0.jar"]
