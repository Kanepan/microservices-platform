package com.zlt.service;

import com.zlt.config.OutSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@EnableBinding(OutSource.MySource.class)
@Service
public class SenderService {
    @Autowired
    private  OutSource.MySource mySource;


    public boolean orderFood(String str) {
//        System.out.println(str);
        return mySource.order().send(MessageBuilder.withPayload(str).build());
    }
}
