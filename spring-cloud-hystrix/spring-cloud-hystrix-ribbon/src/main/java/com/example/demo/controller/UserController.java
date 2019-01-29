package com.example.demo.controller;

import com.example.demo.service.OrdersServer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by 39450 on 2018/9/30.
 */
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrdersServer ordersServer;


    @RequestMapping({"/getOrders/{id}"})
    public OrdersEntity getOrders(@PathVariable  long id){
        return ordersServer.getOrders(id);
    }

    @RequestMapping("/index/{id}")
    @HystrixCommand(fallbackMethod = "fallbackMethodHystrix")
    public OrdersEntity index(@PathVariable  long id){
        String serverName = "eureka-producer";
        //根据eureka-producer实现客户端负载均衡，默认为轮询调用
        return restTemplate.getForObject("http://"+serverName+"/selectOrders/"+id,OrdersEntity.class);
    }

    /**
     * 熔断方法返回类型，必须要和被调用方法返回类型一致
     */
    public OrdersEntity fallbackMethodHystrix(long id){
        return new OrdersEntity(id,"error",0.0,0);
    }
}
