# spring-cloud-demo
## webUI
![](https://github.com/fengdong99/spring-cloud-demo/blob/master/img/eureka1.png)
![](https://github.com/fengdong99/spring-cloud-demo/blob/master/img/admin1.png)
![](https://github.com/fengdong99/spring-cloud-demo/blob/master/img/admin2.png)

## 修改本地hosts文件，添加eureka
    127.0.0.1 eureka1
    127.0.0.1 eureka2
## eureka-server demo服务
    spring.application.name=eureka-server
    server.port=8098
    
    spring.application.name=eureka-server
    server.port=8099

    web访问eureka服务注册与服务发现集群
    http://eureka1:8098
    http://eureka1:8099
    
    其他测试服务器都注册到eureka-server服务上
    eureka.client.serviceUrl.defaultZone=http://eureka1:8098/eureka/,http://eureka2:8099/eureka/

## producer demo服务
    spring.application.name=eureka-producer
    server.port=8101
    
    spring.application.name=eureka-producer
    server.port=8102

## eureka-client demo测试
    spring.application.name=eureka-client
    server.port=8103
    
    查询注册到eureka服务
    http://localhost:8103/getServerList
    ["eureka-producer","eureka-server","service-admin","eureka-client"]
    
    查询订单id为2的信息
    http://localhost:8103/getOrders/2
    {"id":2,"goodsName":"苹果手机","price":8000.0,"num":20}

## client-ribbon demo测试
    spring.application.name=client-ribbon
    server.port=8104
    http://localhost:8104/getOrders/2

## client-feign demo测试
    spring.application.name=client-feign
    server.port=8105
    http://localhost:8105/getOrders/2
    测试结果：producer1服务和producer2服务轮流被访问

## hystrix-feign demo熔断测试
    开启熔断；需要显示开启
    feign.hystrix.enabled=true
    
    spring:
      application:
        name: hystrix-feign
    server:
      port: 8106
    http://localhost:8106/getOrders/2
    正常访问结果：{"id":2,"goodsName":"苹果手机","price":8000.0,"num":20}
    熔断测试方式：关闭producer1服务和producer2服务
    熔断测试结果：{"id":2,"goodsName":"error","price":0.0,"num":0} --指定默认返回结果

## hystrix-ribbon demo熔断测试
    spring:
      application:
        name: hystrix-ribbon
    server:
      port: 8107
    http://localhost:8107/getOrders/2
    http://localhost:8107/index/2

## zuul demo测试
    spring.application.name=api-gateway
    server.port=8108
    zuul.routes.api-feign.path=/api-feign/**
    zuul.routes.api-feign.serviceId=hystrix-feign
    
    zuul.routes.api-ribbon.path=/api-ribbon/**
    zuul.routes.api-ribbon.serviceId=hystrix-ribbon
    
    web测试
    http://localhost:8108/api-feign/getOrders/2 --会路由到hystrix-feign服务上
    http://localhost:8108/api-ribbon/getOrders/2 --会路由到hystrix-ribbon服务上


## admin demo服务
    spring.application.name=service-admin
    http://localhost:8200
