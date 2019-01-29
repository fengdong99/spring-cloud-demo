package com.example.demo.service;

import com.example.demo.controller.OrdersEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by 39450 on 2018/10/easyui.
 */
@Service
public class OrdersServer {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * Hystrix默认熔断阈值：5秒20次
     * hystrix+ribbon:在@HystrixCommand上添加固定的返回方法
     */
    @HystrixCommand(fallbackMethod = "fallbackMethodHystrix")
    public OrdersEntity getOrders(@PathVariable long id){
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

    /**
     * 熔断方法返回类型，必须要和被调用方法返回类型一致
     */
    public OrdersEntity fallbackMethodHystrix(long id){
        return new OrdersEntity(id,"error",0.0,0);
    }


}
