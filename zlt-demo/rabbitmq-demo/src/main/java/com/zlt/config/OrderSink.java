package com.zlt.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderSink {

    String INPUT = "input";

    /**
     * @return input channel.
     */
    @Input(OrderSink.INPUT)
    SubscribableChannel input();
}
