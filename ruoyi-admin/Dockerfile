FROM openjdk:8-jdk
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ADD target/ruoyi-admin.jar /ruoyi-admin.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:InitialRAMPercentage=50.0","-XX:MaxRAMPercentage=50.0","-Duser.timezone=Asia/Shanghai","-jar","/ruoyi-admin.jar"]