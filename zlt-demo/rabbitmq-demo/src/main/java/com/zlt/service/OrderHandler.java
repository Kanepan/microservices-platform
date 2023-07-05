package com.zlt.service;

import com.zlt.config.OrderSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

@EnableBinding(OrderSink.class)
@Slf4j
public class OrderHandler {

    @StreamListener(OrderSink.INPUT)
    public void loggerSink(@Headers MessageHeaders headers, byte[] payload) {
        String order = new String(payload);
//        log.info("order :{}", order);
    }
}

