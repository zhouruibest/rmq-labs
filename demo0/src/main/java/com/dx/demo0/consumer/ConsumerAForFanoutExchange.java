package com.dx.demo0.consumer;


import com.dx.demo0.entity.MessageForFanoutExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MessageForFanoutExchange.QUEUE_NAMEA)
public class ConsumerAForFanoutExchange {
    private Logger logger = LoggerFactory.getLogger(ConsumerAForFanoutExchange.class);

    @RabbitHandler
    public void onMessage(MessageForFanoutExchange message) {
        logger.info("接收到消息: " + message);
    }
}
