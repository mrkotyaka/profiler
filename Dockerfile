FROM openjdk:21-slim
EXPOSE 8081
ADD build/libs/profiler-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]