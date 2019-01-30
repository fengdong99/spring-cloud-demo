package com.example.demo.consumer;


import com.example.demo.common.MessageEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SimpleConsumer.class);


    private final Gson gson = new Gson();

    /**
     * @param message
     */
    @KafkaListener(topics = "${kafka.consumer.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void receive(MessageEntity message) {
        log.info("Consumer receive:{}",gson.toJson(message));
    }

    @KafkaListener(topics = "${kafka.consumer.topic1}", containerFactory = "kafkaListenerContainerFactory")
    public void receive1(MessageEntity message) {
        log.info("Consumer receive1:{}",gson.toJson(message));
    }

}