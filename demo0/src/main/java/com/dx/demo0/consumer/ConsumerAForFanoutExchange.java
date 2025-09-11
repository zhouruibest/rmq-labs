package com.dx.demo0.consumer;


import com.dx.demo0.entity.MessageForFanoutExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerAForFanoutExchange {
    private Logger logger = LoggerFactory.getLogger(ConsumerAForFanoutExchange.class);

    @RabbitListener(queues = MessageForFanoutExchange.QUEUE_NAMEA)
    public void onMessage(MessageForFanoutExchange message) {
        logger.info("AAAAA 接收到消息: " + message);
    }
}
