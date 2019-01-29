package com.example.demo.controller;

import java.io.Serializable;

/**
 * Created by 39450 on 2018/9/30.
 */
public class OrdersEntity implements Serializable{

    private long id;
    private String goodsName;
    private Double price;
    private int num;

    public OrdersEntity() {

    }

    public OrdersEntity(long id, String goodsName, Double price, int num) {
        this.id = id;
        this.goodsName = goodsName;
        this.price = price;
        this.num = num;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
