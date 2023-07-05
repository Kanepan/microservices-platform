package com.zlt.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public class OutSource {
    public interface MySource {
        @Output("order")
        MessageChannel order();
    }

}
