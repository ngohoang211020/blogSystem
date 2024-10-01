##### Dockerfile #####
FROM maven:3.8.3-openjdk-17 as build
WORKDIR ./app
COPY . .
RUN mvn install -DskipTests=true

FROM openjdk:17-alpine
WORKDIR /run
COPY --from=build /app/target/blog-system.jar /run/blog-system.jar
RUN unlink /etc/localtime;ln -s  /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime
# Set the working directory
ENTRYPOINT ["java", "-jar", "/run/blog-system.jar"]
EXPOSE 8080
