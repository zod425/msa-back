FROM openjdk:11-jre-slim

EXPOSE 8080

ADD ./build/libs/sms-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=k8s","-Duser.timezone=Asia/Seoul","/app.jar"]
