FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar
ARG SPRING_PROFILE
RUN echo "jar file: ${JAR_FILE}"
RUN echo "spring.profiles.active: ${SPRING_PROFILE}"
ENTRYPOINT ["java", "-Duser.timezone=GMT+9", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "/app.jar"]