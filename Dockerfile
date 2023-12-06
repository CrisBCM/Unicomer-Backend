FROM amazoncorretto:19

MAINTAINER CRISTIAN

COPY target/unicomer-0.0.1-SNAPSHOT.jar unicomer-0.0.1-SNAPSHOT.jar.original

ENTRYPOINT ["java","-jar","/springboot-0.0.1-SNAPSHOT.jar"]