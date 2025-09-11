package com.dx.demo0.consumer;


import com.dx.demo0.entity.MessageForFanoutExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class ConsumerBForFanoutExchange {

    private Logger logger = LoggerFactory.getLogger(ConsumerBForFanoutExchange.class);

    @RabbitListener(queues = MessageForFanoutExchange.QUEUE_NAMEB)
    public void onMessage(MessageForFanoutExchange message) {
        logger.info("BBBBB 接收到消息: " + message);
    }
}
