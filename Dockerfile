FROM adoptopenjdk/openjdk11:latest
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} /app/cryptocurrency-prices-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-Djava.security.egd=file:/dev/./urandom","-jar","/app/cryptocurrency-prices-tracker.jar"]