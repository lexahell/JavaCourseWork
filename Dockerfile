FROM openjdk:17

WORKDIR /app

COPY /build/libs/JavaCourseWork-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]