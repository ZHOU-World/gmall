## 搭建提供者方

* 提供者方
    * pms - 谷粒商城商品管理系统 18081
    * ums - 谷粒商城用户管理系统 18082
    * wms - 谷粒商城库存管理系统 18083
    * oms - 谷粒商城订单管理系统 18084
    * sms - 谷粒商城营销管理系统 18085

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610132336.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/1624329013(1).jpg)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210615202057.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210615202303.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210615202551.png)

* 搭建微服务的四个步骤
    * 依赖pom.xml
        * 以 gmall 工程作为父工程(父工程中统一管理了我们需要 jar 包的版本号)
        * 删除每个模块中的 dependencyManagement 依赖管理(父工程已经管理好了)
        * 删除 properties 版本号管理(父工程已经管理好了)
        * 引入每个模块所需要的依赖
            * 添加 common 工程(通用模块)
            * 添加 boot 的启动器
                * web
                    * spring-boot-starter-web
                * redis
                    * spring-boot-starter-data-redis
                * mysql 驱动
                    * mysql-connector-java
                * mybatis-plus
                    * mybatis-plus-boot-starter
            * 添加 cloud 的启动器
                * nacos
                    * spring-cloud-starter-alibaba-nacos-config
                    * spring-cloud-starter-alibaba-nacos-discovery
                * sentinel
                    * spring-cloud-starter-alibaba-sentinel
                * openfeign
                    * spring-cloud-starter-openfeign
                * zipkin
                    * spring-cloud-starter-zipkin
    * 修改配置
        * bootstrap.yml
        * application.yml
    * 给启动类添加注解
        * @EnableDiscoveryClient
        * @EnableFeignClients
        * @EnableSwagger2
        * @MapperScan("com.atguigu.gmall.pms.mapper")
    * 代码(逆向工程生成 gmall-generator)
        * application.yml：数据源地址
        * generator.properties：模块名 和 表前缀
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<!--
	所有的工程都需要以 gmall 作为父亲工程
		在我们的父亲工程中同意管理了版本号
			SpringCloud, SpringCloud Alibaba
			mybatisplus, mysql, swagger, lombok
			jjwt, joda(时间操作工具包), redisson(分布式锁相关), lang3(工具包)
		 创建springboot工程, 默认是以spring-boot-starter-parent为父工程, 需要改为 gmall 为父工程
	 -->
	<parent>
		<groupId>com.atguigu</groupId>
		<artifactId>gmall</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>

	<groupId>com.atguigu</groupId>
	<artifactId>gmall-pms</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>gmall-pms</name>
	<description>谷粒商城商品管理系统</description>

	<dependencies>

		<!-- 引入 common 工程 -->
		<dependency>
			<groupId>com.atguigu</groupId>
			<artifactId>gmall-common</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>

		<!-- springboot 相关依赖 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
		</dependency>

		<!-- springcloud 相关依赖 -->
		<dependency>
			<groupId>com.alibaba.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610142824.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610143055.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610143302.png)

```yaml
spring:
  application:
    name: pms-service
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        namespace: 912552c3-5ed1-455f-ae40-25d11cc30cd8
        group: dev
        file-extension: yml
```

```yaml
server:
  port: 18081
spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    sentinel:
      transport:
        dashboard: localhost:8080
        port: 8719
  sleuth:
    sampler:
      probability: 1
  zipkin:
    base-url: http://localhost:9411
    sender:
      type: web
    discovery-client-enabled: false
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.12.13:3306/guli_pms
    username: root
    password: root
  redis:
    host: 192.168.12.13
    port: 6379
feign:
  sentinel:
    enabled: true
mybatis-plus:
  mapper-locations: classpath:mapper/pms/**/*.xml
  type-aliases-package: com.atguigu.gmall.pms.entity
  global-config:
    db-config:
      id-type: auto
```