# Flow

草稿

Flow 修改自 MaxLeap MyBaaS 开源版本，只是个为了将各种知识融会贯通的练手项目，不具备任何商业价值。

预计包含的内容

- 基础框架：Vert.x3/Node + Spring Boot/Rails/Grails
- 服务器：Jetty + Nginx
- 数据存储：
	- 提供类似 Jooq 的方式访问 MongoDB, MySQL, H2
- 文件存储：
	- 支持 GridFS，S3
- 移动端支持：
	- 原生 Android 和 iOS SDK
	- ReactNative SDK
- 数据分析：
	- 基于 Spark Core 离线分析
	- 基于 Spark Streaming 实时分析
- 日志分析：ELK
- 各种控制台 UI：
	- 基于 React Starter Kit + Material UI + Jade
- 监控系统：Nagios
- 工具：
	- 类似 rails 的资源文件创建工具
	- 类似 rake 的数据迁移工具
	- 提供自动环境配置的 Android Gradle 插件
	- 支持 Java 和 Android 的 Apt 工具
- 运行环境：
	- Fat Jar
	- Docker 镜像

目前进度：0%

- - -


![maxbaas-logo](https://avatars2.githubusercontent.com/u/11622934?v=3&s=200)

# MyBaaS

MyBaaS是 [MaxLeap](https://www.maxleap.cn) CloudData和CloudCode部分的开源版本，更多功能请前往 [MaxLeap](https://www.maxleap.cn)。

## 1.0 功能特性
* 多应用
* CloudData
* CloudCode
* Permission & ACL
* Users

## 1.0 不包含的功能
* 邮件验证
* 短信登录
* CloudCode管理

## 快速开始
* 安装 mongodb3.0+
* 安装 maven3.0+
* 安装 git client
* 安装 JDK 1.8+
* git clone https://gitlab.maxleap.com/maxleapservices/maxleap-baas.git
* cd maxleap-baas
* ./build.sh

## 运行
* 1、修改配置文件(配置mongo地址(默认localhost)) vi build/bin/bootstrap
* 2、启动运行 ./build/bin/bootstrap
     启动运行(有cloudcode，需要将cloudcode的jar放置在build/bin目录下) ./build/bin/bootstrap-cloudcode
* 3、演示地址 http://localhost:10086 （账户密码：admin123@gmail.com/admin123）
