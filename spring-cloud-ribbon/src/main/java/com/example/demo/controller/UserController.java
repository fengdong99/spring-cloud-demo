package com.example.demo.controller;

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
    private DiscoveryClient discoveryClient;

    @RequestMapping("/getServerList")
    public List<String> getServerList(){
        List<String> server = discoveryClient.getServices();
        return server;
    }

    @RequestMapping({"/getOrders/{id}"})
    public OrdersEntity getOrders(@PathVariable  long id){
        String serverName = "eureka-producer";
        List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
        if( instances == null || instances.isEmpty()){
         return null;
        }
        instances.stream().forEach(f -> {
            System.out.println("Host:"+ f.getHost());
            System.out.println("Port:"+ f.getPort());
            System.out.println("Uri:"+ f.getUri());
            System.out.println("ServiceId:"+ f.getServiceId());
            System.out.println("Metadata:"+ f.getMetadata());
            System.out.println("Scheme:"+ f.getScheme());
        });

        //根据eureka-producer实现客户端负载均衡，默认为轮询调用
        OrdersEntity  ordersEntity = restTemplate.getForObject("http://"+serverName+"/selectOrders/"+id,OrdersEntity.class);

        return ordersEntity;
    }
}
