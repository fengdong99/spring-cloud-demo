package com.example.demo.service;

import com.example.demo.entity.OrdersEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 *
 */
//通过@FeignClient指定调用的服务
@FeignClient(value = "eureka-producer")
public interface OrdersService {

    //指定服务的路由
    @RequestMapping({"/selectOrders/{id}"})
    public OrdersEntity getOrders(@PathVariable("id") long id);
}
