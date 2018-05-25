# springCloudExample

## **一：基础环境准备：**

### 1.安装docker
### 2.安装kafka
#### 1）下载镜像
  docker pull wurstmeister/zookeeper    
  docker pull wurstmeister/kafka    
#### 2）启动镜像
docker run -d --name zookeeper --publish 2181:2181 --volume /etc/localtime:/etc/localtime zookeeper:latest  

docker run -d --name kafka --publish 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181   
--env KAFKA_ADVERTISED_HOST_NAME=kafka所在宿主机的IP --env KAFKA_ADVERTISED_PORT=9092 --volume /etc/localtime:/etc/localtime   
wurstmeister/kafka:latest
## **二：环境运行**
### 1.启动多个注册中心
#### 1）进入cd legou-eureka/
执行：mvn install docker:build 生成镜像
#### 2）启动三个注册中心
  docker run -p 8081:8081 -t legou/legou-eureka --spring.profiles.active=discovery1
  
  docker run -p 8082:8082 -t legou/legou-eureka --spring.profiles.active=discovery2
  
  docker run -p 8083:8083 -t legou/legou-eureka --spring.profiles.active=discovery3
### 2.启动服务提供者
#### 1) 进入cd legou-server/
执行：mvn install docker:build 生成镜像
#### 2）启动两个服务
docker run -e SERVER_PORT=8766 -e SERVER_IP=10.58.93.146 -p 8766:8766 -t legou/legou-server

docker run -e SERVER_PORT=8765 -e SERVER_IP=10.58.93.146 -p 8765:8765 -t legou/legou-server
### 3.启动服务消费者
#### 1）cd legou-api 
执行：mvn install docker:build 生成镜像
#### 2)启动两个服务
docker run -e SERVER_PORT=8088 -p 8088:8088 -t legou/legou-api

docker run -e SERVER_PORT=8089 -p 8089:8089 -t legou/legou-api
#### 3)限流
http://10.58.93.148:8088/hystrix/monitor?stream=http%3A%2F%2F10.58.93.148%3A8088%2Fhystrix.stream
#### 4)接口文档
http://10.58.93.148:8088/swagger-ui.html#/hello-controler
### 4.启动网关
#### 1）cd legou-zuul/target
#### 2)启动网关
java -jar legou-zuul-1.0-SNAPSHOT.jar(暂时先没有做docker运行)
可以验证groovy动态filter
### 5.启动配置管理服务端
#### 1）cd config-server
执行：mvn install docker:build 生成镜像
#### 2)启动两个服务
docker run -e SERVER_PORT=7088 -p 7088:7088 -t legou/config-server

docker run -e SERVER_PORT=7089 -p 7089:7089 -t legou/config-server

可以访问http://10.58.93.148:7088/config-client/dev 看是能取到配置文件

### 6.启动配置管理客户端
#### 1）cd config-client
mvn install docker:build
#### 2)启动两个服务
docker run -e SERVER_PORT=6088 -p 6088:6088 -t legou/config-client

docker run -e SERVER_PORT=6089 -p 6089:6089 -t legou/config-client

改变配置文件，刷新配置管理端post http://10.58.93.148:7088/bus/refresh 看是否bus正常，服务端能够获取更新后的配置

### 7.启动调用链跟踪的客户端和搜集端（还没做数据持久化）
#### 1）cd zipkin-server/
mvn install docker:build
#### 2)启动两个服务
docker run -e SERVER_PORT=9888 -p 9888:9888 -t legou/zipkin-server
访问http://10.58.93.148:9888/zipkin/  刷新legu-api接口 看调用链信息

## **三：docker基本命令使用**
### 查看镜像
docker images
### 移除镜像
docker rmi 镜像id或名
### 查看运行的服务
docker ps
### 查看所有服务包含运行的
docker ps -a
### 停止容器服务
docker stop 容器id或名
### 启动容器
docker start 容器id或名
### 移除容器
docker rm 容器id或名
### 创建容器并运行
docker run

