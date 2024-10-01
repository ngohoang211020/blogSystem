##### Dockerfile #####
FROM maven:3.8.3-openjdk-17 as build
WORKDIR ./src
COPY . .
RUN mvn install -DskipTests=true

FROM openjdk:17-alpine
COPY --from=build /target/blog-system.jar blog-system.jar
RUN unlink /etc/localtime;ln -s  /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime
# Set the working directory
ENTRYPOINT ["java", "-jar", "blog-system.jar"]
EXPOSE 8080
