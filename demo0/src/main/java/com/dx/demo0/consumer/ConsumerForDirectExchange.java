package com.dx.demo0.consumer;

import com.dx.demo0.entity.MessageForDirectExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class ConsumerForDirectExchange {
    private Logger logger = LoggerFactory.getLogger(ConsumerForDirectExchange.class);

    //@RabbitHandler
    @RabbitListener(queues = MessageForDirectExchange.QUEUE_NAME)
    public void onMessage(Message message) {
        logger.info("接收到消息: " + message);
        logger.info("消息头: {}", message.getMessageProperties().getHeaders());
        // 打印消息体（原始字节流）
        logger.info("消息体: {}", new String(message.getBody()));
        // 打印 content-type
        logger.info("content-type: {}", message.getMessageProperties().getContentType());
    }
}
