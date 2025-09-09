package com.dx.demo0.producer;

import com.dx.demo0.Demo0Application;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(classes = Demo0Application.class)
public class TopicExchangeTest {
    private Logger logger = LoggerFactory.getLogger(TopicExchangeTest.class);

    @Autowired
    private ProducerForTopicExchange producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend("66666666");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend("777777").whenComplete((result, throwable) -> {
            if (throwable != null) {
                logger.error("对象消息发送异常[correlationId: " + id + "]：" + throwable.getMessage() + "%n" );
            } else {
                logger.info("对象消息发送操作完成[correlationId: " + id + "]" + result.getClass() +  "%n");
            }
        });

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
