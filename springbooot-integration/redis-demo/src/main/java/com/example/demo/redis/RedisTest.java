package com.example.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by 39450 on 2019/4/29.
 */
public class RedisTest {

    public static void main(String[] args) {

        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(100);//可用最大连接数
        config.setMaxIdle(100);//最大空闲连接数
        config.setMaxWaitMillis(5000);//最长等待时间
        config.setTestOnBorrow(true);//在获取redis连接时，自动检测连接是否有效
        JedisPool jedisPool = new JedisPool(config,"127.0.0.1",6379,6000,"123456");//超时时间：6s

        Jedis jedis = jedisPool.getResource();

        jedis.set("key123","123123");

        System.out.println(jedis.get("key123"));

    }
}
