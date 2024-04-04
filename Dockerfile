FROM maven:3.9-amazoncorretto-21 as backend
WORKDIR /backend
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean install -Dmaven.test.skip=true -P dev
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../blog-system.jar)

FROM openjdk:17-alpine
ARG DEPENDENCY=/backend/target/dependency
COPY --from=backend ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=backend ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=backend ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-jar", "blog-system.jar"]
EXPOSE 8080