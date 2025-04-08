## ☘️项目简介

> 项目地址：http://152.32.210.209:8100/
>
> * 账号：admin
>
> * 密码：123456

Wick-Boot 是一个基于 [vue3-element-plus](https://gitee.com/youlaiorg/vue3-element-admin) 构建的前后端分离单体多模块管理系统。目前采用 JDK8 + SpringBoot2 + Vue3 进行开发，后续会升级为 JDK17。

* 开源项目地址

| 项目名称  | Github                                    |
| --------- | ----------------------------------------- |
| Wick-Boot | https://github.com/wicksonZhang/Wick-Boot |
| Wick-Web  | https://github.com/wicksonZhang/Wick-Web  |

* 项目结构

```shell
├─wick-admin-server              // 项目启动类
│  
├─wick-commons                   // 项目工具父模块
│  ├─wick-common-core            // 项目基础模块
│  ├─wick-common-log             // 项目日志模块
│  ├─wick-common-mybatis         // 项目数据库模块
│  ├─wick-common-redis           // 项目缓存模块
│  ├─wick-common-security        // 项目安全模块
│  ├─wick-common-web             // 项目Web模块
│  ├─wick-common-websocket       // 项目WebSocket模块
│  └─wick-common-xxl-job         // 项目定时任务模块
│ 
├─wick-module-auth               // 认证模块
|
├─wick-module-system             // 系统管理模块
│  ├─wick-module-system-api      // 系统管理模块API
│  └─wick-module-system-boot     // 系统管理模块启动类
|
├─wick-sync-db-flyway            // 数据库同步工具
|
```




## 🌴系统功能

> 这个项目虽然是基于  [vue3-element-plus](https://gitee.com/youlaiorg/vue3-element-admin) 进行构建的，但还是扩展原项目扩展了很多不同的功能。
>
> 其中针对系统工具重新构建、系统监控集成了XXL-JOB。

### 系统管理

> 系统管理模块包括：用户管理、角色管理、菜单管理、部门管理、字典管理、日志管理（登录日志、操作日志）

| 模块（系统管理） |                           页面信息                           |
| :--------------: | :----------------------------------------------------------: |
|     用户管理     | ![image-20250408182052434](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081820509.png) |
|     角色管理     | ![image-20250408183154231](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081831308.png) |
|     菜单管理     | ![image-20250408183242948](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081832019.png) |
|     部门管理     | ![image-20250408183311011](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081833086.png) |
|     字典管理     | ![image-20250408183325414](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081833485.png) |
|     登录日志     | ![image-20250408183354046](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081833128.png) |
|     操作日志     | ![image-20250408183409138](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081834205.png) |

### 系统工具

> 系统工具模块包括：数据源配置、代码生成

| 模块（系统工具） |                           页面信息                           |
| :--------------: | :----------------------------------------------------------: |
|    数据源配置    | ![image-20250408183619328](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081836392.png) |
|     代码生成     | ![image-20250408183638725](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081836798.png) |

### 系统监控

> 系统监控模块包括：在线用户、定时任务（执行器管理、定时任务管理、调度日志）

| 模块（系统监控） |                           页面信息                           |
| :--------------: | :----------------------------------------------------------: |
|     在线用户     | ![image-20250408184138092](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081841170.png) |
|    执行器管理    | ![image-20250408184154694](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081841772.png) |
|   定时任务管理   | ![image-20250408184210714](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081842790.png) |
|     调度日志     | ![image-20250408184226287](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081842361.png) |



## 🔨技术栈

该项目主要采用的还是比较常用的技术栈，如下是具体信息：

| 框架         | 说明               | 版本                |
| ------------ | ------------------ | ------------------- |
| SpringBoot   | MVC框架            | 2.6.3               |
| MyBatis-Plus | ORM框架            | 3.5.3.1             |
| knife4j      | 文档生产工具       | 3.0.0               |
| Redis        | 分布式缓存         | 1.0.0-JDK8-SNAPSHOT |
| Lombok       | 简化对象封装工具   | 1.18.26             |
| Hutool       | Java工具包类库     | 5.8.25              |
| XXL-JOB      | 定时任务           | 2.4.1               |
| Flyway       | 数据库版本管理工具 | 7.15.0              |
| MySQL        | 数据库服务器       | 5.7.35              |
| Docker       | 容器化部署         | 26.1.4              |
| Vue          | 渐进式框架         | 3.5.13              |
| Vite         | 前端构建工具       | 5.2.3               |
| Element-Plus | 前端UI库           | 2.9.7               |



## 🍊项目启动

### 准备工作

> 由于该项目内置了 Flyway 所以不需要导入对应的数据库，只需要创建数据库即可。

* 创建数据库

```sql
mysql> CREATE DATABASE `wick_boot` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
Query OK, 1 row affected (0.00 sec)
```

* 运行项目

![image-20250408190215056](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081902121.png)



### 测试启动

> http://localhost:8080/api/v1/doc.html

![image-20250408190500613](https://cdn.jsdelivr.net/gh/wicksonZhang/static-source-cdn/images/202504081905674.png)



## 许可证

本项目使用 [Apache 2.0 许可证](LICENSE)。