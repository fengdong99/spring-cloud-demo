package com.example.demo.controller;

import com.example.demo.entity.OrdersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/getServerList")
    public List<String> getServerList(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-server");
        System.out.println("eureka-server:" + serviceInstance.getServiceId() + ":" + serviceInstance.getHost() + ":" + serviceInstance.getPort());

        ServiceInstance producer = loadBalancerClient.choose("eureka-producer");
        System.out.println("eureka-producer:" + producer.getServiceId() + ":" + producer.getHost() + ":" + producer.getPort());

        List<String> server = discoveryClient.getServices();
        return server;
    }

    @RequestMapping({"/getOrders/{id}"})
    public OrdersEntity getOrders(@PathVariable  long id){
        String serverId = "eureka-producer";
        List<ServiceInstance> instances = discoveryClient.getInstances(serverId);
        if( instances == null || instances.isEmpty()){
         return null;
        }
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort();
        OrdersEntity  ordersEntity = restTemplate.getForObject(url+"/selectOrders/"+id,OrdersEntity.class);
        return ordersEntity;
    }
}
