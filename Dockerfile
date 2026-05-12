FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/ruoyi-admin/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]