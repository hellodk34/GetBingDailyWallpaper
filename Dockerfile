FROM openjdk:8-jdk-alpine 

WORKDIR /app

ADD bingwallpaper.jar app.jar

EXPOSE 8011

ENTRYPOINT ["java","-jar","app.jar"]
