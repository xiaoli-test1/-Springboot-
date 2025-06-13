# 17或更高版本的基础镜像（Spring Boot 3.x需要）
FROM eclipse-temurin:17-jdk-jammy

# 设置工作目录
WORKDIR /app

# 拷贝JAR文件到容器的工作目录（明确目标路径）
COPY target/SpringSecurity-0.0.1-SNAPSHOT.jar /app/app.jar

# 暴露应用端口（如8080）
EXPOSE 8080

# 设置JVM参数（可选）
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# 启动命令（使用绝对路径和环境变量）
CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]