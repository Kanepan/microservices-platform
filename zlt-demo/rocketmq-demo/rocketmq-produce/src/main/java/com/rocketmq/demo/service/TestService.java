package com.rocketmq.demo.service;

import com.rocketmq.demo.model.Order;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TestService {

    @Autowired
    private SenderService senderService;

    public BigDecimal test(Long count) {

        return send(count);
    }

    private static ExecutorService es = Executors.newFixedThreadPool(120);

    public BigDecimal send(Long c) {
        long count = 100L;

        AtomicLong success = new AtomicLong(0);

        if (c != null) {
            count = c.intValue();
        }

        List<Callable<Boolean>> tasks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final int j = i;
            tasks.add(() -> {
                boolean flag = sendMsg(j);
                if (flag) {
                    success.incrementAndGet();
                }
                return flag;
            });
        }

        //打乱顺序
//        Collections.shuffle(tasks);

        long time = System.currentTimeMillis();

        try {
            es.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long time2 = System.currentTimeMillis();
        long cost = (time2 - time) / 1000;

        System.out.println("cost:" + cost + "s");
        BigDecimal a = new BigDecimal(success.get());
        System.out.println("send count:" + count);
        System.out.println("success count:" + success.get());
        BigDecimal ccc = new BigDecimal(count);
        System.out.println("success rate:" + a.divide(ccc, 3, RoundingMode.HALF_UP));

        BigDecimal b = new BigDecimal(time2 - time);
        System.out.println("avs:" + b.divide(a, 0, RoundingMode.HALF_UP) + "ms");


        a = new BigDecimal(success.get()).multiply(new BigDecimal(1000));
        b = new BigDecimal(time2 - time);

        System.out.println("tps:" + a.divide(b, 2, RoundingMode.HALF_UP));


        return a.divide(b, 2, RoundingMode.HALF_UP);
    }

    private boolean sendMsg(int index) {
        String memo = RandomStringUtils.random(500, true, true);
        return senderService.sendWithTags(new Order((long) index, "order-" + index,memo), "tagObj");
    }
}
