
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8081
ADD ./target/delta-web-facade-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]