package com.rocketmq.demo.service;

import org.apache.rocketmq.common.message.MessageConst;
import com.rocketmq.demo.config.RocketMqConfig.MySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * @author zlt
 */
@Service
public class SenderService {
    @Autowired
    private MySource source;

    /**
     * 发送字符消息
     */
    public boolean send(String msg) {
        return source.output().send(MessageBuilder.withPayload(msg).build());
    }

    /**
     * 发送带tag的对象消息
     */
    public <T> boolean sendWithTags(T msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        try {
            //		source.output().send(message);
            return source.output().send(message, 30000);
        } catch (Exception e) {
            System.out.println("发送超时：" + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
