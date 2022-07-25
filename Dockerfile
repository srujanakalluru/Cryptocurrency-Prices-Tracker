FROM adoptopenjdk/openjdk11
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/crypto-prices-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/crypto-prices-tracker.jar"]