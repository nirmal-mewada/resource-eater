# docker build -t nirmalsuthar/resource_eater .
FROM openjdk:8-jdk-alpine
WORKDIR /
ADD target/resource-eater-1.0.0.jar app.jar
EXPOSE 8080
#CMD java -jar app.jar
ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar
