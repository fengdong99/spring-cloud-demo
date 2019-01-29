package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 39450 on 2018/9/30.
 */

@RestController
public class OrdersController {

    @Value("${server.port}")
    String port ;
    @Value("${spring.application.name}")
    String name ;

    public List<OrdersEntity> ordersEntityList = new ArrayList<>();
    public List<OrdersEntity> getOrdersEntityList() {
        ordersEntityList.add(new OrdersEntity(1,"电视机",5000.0,10));
        ordersEntityList.add(new OrdersEntity(2,"苹果手机",8000.0,20));
        ordersEntityList.add(new OrdersEntity(3,"ThinkPad电脑",12000.0,30));
        return ordersEntityList;
    }

    @RequestMapping({"/selectOrders/{id}"})
    public OrdersEntity selectOrders(@PathVariable  long id){
        OrdersEntity ordersEntity =  getOrdersEntityList().stream().filter(f -> f.getId() == id).collect(Collectors.toList()).get(0);
        System.out.println("name:"+name+"; port:"+port+ " ; ordersEntity:"+ordersEntity);
        return ordersEntity;
    }

}
