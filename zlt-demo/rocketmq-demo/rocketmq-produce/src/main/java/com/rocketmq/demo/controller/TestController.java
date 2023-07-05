package com.rocketmq.demo.controller;


import com.rocketmq.demo.service.SenderService;
import com.rocketmq.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String test(@RequestParam Long count) {
        return testService.test(count).setScale(2).toString();
    }
}
