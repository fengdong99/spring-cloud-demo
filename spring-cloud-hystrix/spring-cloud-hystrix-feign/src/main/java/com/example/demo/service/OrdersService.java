package com.example.demo.service;

import com.example.demo.entity.OrdersEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hystrix+feign:在@FeignClient注解上添加固定的返回接口实现类;接口实现类上需要添加@Component注解
 */
//通过@FeignClient指定调用的服务
@FeignClient(value = "eureka-producer",fallback = OrdersServiceImpHystrix.class)
public interface OrdersService {

    //指定服务的路由
    @RequestMapping({"/selectOrders/{id}"})
    public OrdersEntity getOrders(@PathVariable("id") long id);
}
