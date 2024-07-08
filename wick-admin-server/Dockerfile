# 使用更小的基础镜像
FROM openjdk:8-jre

# 维护者信息
MAINTAINER Wickson <wickson_zhang@163.com>

# 时区设置
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone

# 设置 /tmp 目录作为容器数据卷目录
VOLUME /tmp

# 复制 jar 到镜像
COPY target/wick-admin-server.jar /app.jar
COPY target/classes/application.yml /application.yml
COPY target/classes/application-local.yml /application-local.yml

# 设置容器启动时执行的命令
ENTRYPOINT ["java", "-Xmx128m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

# 暴露端口
EXPOSE 8000
