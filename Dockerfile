# docker build -t nirmalsuthar/resource_eater .
FROM maven:3.6.3-jdk-8 as builder
ENTRYPOINT ["/bin/sh"]
COPY . /
RUN mvn clean package

FROM openjdk:8-jdk-alpine
WORKDIR /
COPY --from=builder  target/resource-eater-1.0.0.jar app.jar
#ADD target/resource-eater-1.0.0.jar app.jar
EXPOSE 8080
#CMD java -jar app.jar
ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar
