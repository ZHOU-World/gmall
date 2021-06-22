## 网关服务

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610163831.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610164010.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610164300.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610164444.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210622125254.png)

* 修改 pom 文件
    * 以 gmall 作为父工程
    * 删除 properties
    * 引入 nacos 依赖
        * spring-cloud-starter-alibaba-nacos-config
        * spring-cloud-starter-alibaba-nacos-discovery
* 配置

```yaml
spring:
  application:
    name: gateway-api
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        namespace: 10e6d2be-5a42-4dea-91a0-46bf52edabd3
        group: dev
        file-extension: yml
```

```yaml
server:
  port: 8888
spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    gateway:
      routes:
        - id: pms-route
          uri: lb://pms-service
          predicates:
            - Path=/pms/**
        - id: sms-route
          uri: lb://sms-service
          predicates:
            - Path=/sms/**
        - id: wms-route
          uri: lb://wms-service
          predicates:
            - Path=/wms/**
        - id: ums-route
          uri: lb://ums-service
          predicates:
            - Path=/ums/**
        - id: oms-route
          uri: lb://oms-service
          predicates:
            - Path=/oms/**
```

## 通过域名访问网关

> 通过域名访问的问题, 开发、测试、生产环境不一致, 避免环境差异带来的问题, 所有的环境都通过域名访问

* 域名访问对应服务器流程
    * 先找本机的 hosts 文件(配置 ip 与域名的关系)
    * 找不到就去找 dns 服务器, 如果没有就是 404

* 给网关设置一个域名(dns 保存的是 域名与服务器的对应关系)
    * C:\Windows\System32\drivers\etc\hosts
        * localhost api.gmall.com manager.gmall.com

* 把 80 端口交给 nginx 由 nginx 反向代理(代理两个工程)
    * vim /usr/local/nginx/conf/nginx.conf

```
http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;

    server {
        listen       80;
        server_name  api.gmall.com; # 区分代理配置 

        location / { # / 所有请求
            proxy_pass http://192.168.18.98:8888; # ip 是宿主机的 ip
        }
    }

    server {
        listen       80;
        server_name  manager.gmall.com;
        
        location / {
            # 宿主机以太网网卡的 ip 
            proxy_pass http://192.168.18.98:1000; # 那个可以在 linux 中 ping 通及设置
        }
    }
```

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610181602.jpg)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610181956.png)

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210610182101.png)

```
用户输入域名首先经过用户主机的 hosts 文件解析, 解析的结果是一个 ip
根据 ip 地址到达 nginx 服务器, nginx 在监听 80 端口号, 通过 80 端口找到 nginx 应用
域名将在头信息中携带给 nginx, 根据头信息的域名找到对应的 server 反向代理的配置
反向代理的配置有一个ip跟端口, 根据 ip 跟端口找到网关的服务器
网关服务器在监听对应的 端口号, 根据端口号找到对应的网关服务
根据 xxx 路径找到对应的路由, 通过路由服务找到对应的服务, 根据路径找到对应的 Controller 方法处理请求

Controller 处理完通过网关将响应送达 nginx, 通过 nginx 将结果响应给用户浏览器
```

![](https://cdn.jsdelivr.net/gh/yiki-oss/imgs@main/20210622141159.png)

