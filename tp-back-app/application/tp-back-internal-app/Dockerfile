FROM openjdk:17-jdk-slim
RUN mkdir backend
ADD ./build/libs ./backend/
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "backend/tp-back-internal-app-1.0.jar"]
