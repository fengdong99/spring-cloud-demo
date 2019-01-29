package com.example.kafka.controller;


import com.example.kafka.common.ErrorCode;
import com.example.kafka.common.KafkaLog4jAppender;
import com.example.kafka.common.MessageEntity;
import com.example.kafka.common.Response;
import com.example.kafka.producer.SimpleProducer;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/kafka")
public class ProduceController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(KafkaLog4jAppender.class);

    @Autowired
    private SimpleProducer simpleProducer;

    @Value("${kafka.topic.default}")
    private String topic;

    @Value("${kafka.topic.default1}")
    private String topic1;

    private Gson gson = new Gson();

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = {"application/json"})
    public Response sendKafka() {
        return new Response(ErrorCode.SUCCESS, "OK");
    }


    @RequestMapping(value = "/send")
    public Response sendKafka(MessageEntity message) {
        try {
            log.info("kafka的消息={}", gson.toJson(message));

            int i = 0;
            i = i / 0;
                simpleProducer.send("topicLog4j", "key", message);
                log.info("发送kafka成功.");

            return new Response(ErrorCode.SUCCESS, "发送kafka成功");
        } catch (Exception e) {
            log.error("发送kafka失败", e);
            return new Response(ErrorCode.EXCEPTION, "发送kafka失败");
        }
    }

    @RequestMapping(value = "/send1")
    public Response sendKafka1(MessageEntity message) {
        try {
            log.info("kafka的消息={}", gson.toJson(message));
            simpleProducer.send(topic1, "key", message);
            log.info("发送kafka成功.");
            return new Response(ErrorCode.SUCCESS, "发送kafka成功");
        } catch (Exception e) {
            log.error("发送kafka失败", e);
            return new Response(ErrorCode.EXCEPTION, "发送kafka失败");
        }
    }

}