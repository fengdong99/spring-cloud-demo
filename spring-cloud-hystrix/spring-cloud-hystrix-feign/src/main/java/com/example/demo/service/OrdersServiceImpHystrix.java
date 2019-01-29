package com.example.demo.service;

import com.example.demo.entity.OrdersEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by 39450 on 2018/10/easyui.
 */
@Component
public class OrdersServiceImpHystrix implements OrdersService {
    @Override
    public OrdersEntity getOrders(@PathVariable("id") long id) {
        return new OrdersEntity(id,"error",0.0,0);
    }
}
