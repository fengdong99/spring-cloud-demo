package com.example.demo.controller;

import com.example.demo.entity.OrdersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class UserController {


    /**
     * 如调用本地方式一样
     */
    @RequestMapping({"/getOrders/{id}"})
    public String getOrders(@PathVariable("id")  long id){


        return id +"";
    }
}
