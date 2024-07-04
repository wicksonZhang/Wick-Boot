# 使用更小的基础镜像
FROM openjdk:8-jdk-alpine

# 维护者信息
LABEL maintainer="Wickson <wickson_zhang@163.com>"

# 时区设置
ENV TZ=Asia/Shanghai
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    apk del tzdata

# 设置 /tmp 目录作为容器数据卷目录
VOLUME /tmp

# 复制 jar 到镜像
COPY target/wick-admin-server.jar /app.jar

# 暴露端口
EXPOSE 8000

# 设置容器启动时执行的命令
ENTRYPOINT ["java", "-Xmx128m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

# 添加健康检查（可选）
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
    CMD curl --fail http://localhost:8000/actuator/health || exit 1
