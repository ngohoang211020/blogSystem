FROM adoptopenjdk:17-jdk-hotspot

# Set the working directory
WORKDIR /app

COPY /target/blog-system.jar blog-system.jar

ENTRYPOINT ["java", "-jar", "blog-system.jar"]

EXPOSE 8080