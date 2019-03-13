# spring-cloud-demo
## 修改本地hosts文件，添加eureka
    127.0.0.1 eureka1
    127.0.0.1 eureka2
## eureka demo服务
    http://eureka1:8098
    http://eureka1:8099

## producer demo服务
    server.port=8101
    server.port=8102

## client demo测试
    server.port=8103
    查询注册到eureka服务
    http://localhost:8103/getServerList
    ["eureka-producer","eureka-server","service-admin","eureka-client"]
    查询订单id为2的信息
    http://localhost:8103/getOrders/2
    {"id":2,"goodsName":"苹果手机","price":8000.0,"num":20}

## lient-ribbon demo测试
    server.port=8104
    http://localhost:8104/getOrders/2

## client-feign demo测试
    server.port=8105
    http://localhost:8105/getOrders/2
    测试结果：producer1服务和producer2服务轮流被访问

## hystrix-feign demo熔断测试
    server.port=8106
    http://localhost:8106/getOrders/2
    正常访问结果：{"id":2,"goodsName":"苹果手机","price":8000.0,"num":20}
    熔断测试方式：关闭producer1服务和producer2服务
    熔断测试结果：{"id":2,"goodsName":"error","price":0.0,"num":0} --指定默认返回结果

## hystrix-ribbon demo测试
    server.port=8107
    http://localhost:8107/getOrders/2
    http://localhost:8107/index/2

## zuul demo测试
    server.port=8108
    http://localhost:8108/api-feign/getOrders/2 --会路由到hystrix-feign服务上
    http://localhost:8108/api-ribbon/getOrders/2 --会路由到hystrix-ribbon服务上


## admin demo服务
    http://localhost:8200

