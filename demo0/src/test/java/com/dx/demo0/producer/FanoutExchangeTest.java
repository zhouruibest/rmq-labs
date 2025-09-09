package com.dx.demo0.producer;

import com.dx.demo0.Demo0Application;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(classes = Demo0Application.class)
public class FanoutExchangeTest {
    private Logger logger = LoggerFactory.getLogger(FanoutExchangeTest.class);

    @Autowired
    private ProducerForFanoutExchange producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int)(System.currentTimeMillis() / 1000);
        producer.syncSend("111111111111");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
        new CountDownLatch(1).await();
    }
}
