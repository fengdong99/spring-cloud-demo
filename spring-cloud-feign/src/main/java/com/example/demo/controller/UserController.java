package com.example.demo.controller;

import com.example.demo.entity.OrdersEntity;
import com.example.demo.service.OrdersService;
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
    private OrdersService ordersService;


    /**
     * 如调用本地方式一样
     */
    @RequestMapping({"/getOrders/{id}"})
    public OrdersEntity getOrders(@PathVariable("id")  long id){
        OrdersEntity ordersEntity =  ordersService.getOrders(id);
        return ordersEntity;
    }
}
