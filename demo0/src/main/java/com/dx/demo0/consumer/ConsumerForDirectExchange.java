package com.dx.demo0.consumer;

import com.dx.demo0.entity.MessageForDirectExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MessageForDirectExchange.QUEUE_NAME, ackMode = "AUTO")
public class ConsumerForDirectExchange {
    private Logger logger = LoggerFactory.getLogger(ConsumerForDirectExchange.class);

    @RabbitHandler
    public void onMessage(MessageForDirectExchange message) {
        logger.info("Received message: " + message);
    }
}
