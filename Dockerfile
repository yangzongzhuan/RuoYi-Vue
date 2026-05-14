# 1. 基础镜像
FROM openjdk:8-jre-slim

# 2. 设置工作目录
WORKDIR /app

# 3. 复制打包好的 jar 包 (根据你实际的 jar 包名称修改)
# 注意：这需要你在 GitHub Actions 步骤中先进行 mvn clean package
COPY ./ruoyi-admin/target/ruoyi-admin.jar /app/app.jar

# 4. 暴露后端端口（RuoYi 默认是 8080）1
EXPOSE 8080

# 5. 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]