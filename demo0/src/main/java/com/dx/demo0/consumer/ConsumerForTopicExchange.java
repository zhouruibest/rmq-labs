package com.dx.demo0.consumer;


import com.dx.demo0.entity.MessageForTopicExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;


@Component

public class ConsumerForTopicExchange {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = MessageForTopicExchange.QUEUE_NAME)
    public void onMessage(MessageForTopicExchange message, Channel channel, Message amqpMessage) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}], start", Thread.currentThread().getId(), message);
        try {
            Thread.sleep(1000);
            String replyContent = "消费成功" + message.getMessage();
            String reployTo = amqpMessage.getMessageProperties().getReplyTo();
            String correlationId = amqpMessage.getMessageProperties().getCorrelationId();
            if (reployTo != null && correlationId != null) {
                logger.info("[onMessage][线程编号:{} 消息内容：{}], 准备回复消息：{}",
                        Thread.currentThread().getId(), message, replyContent);
                rabbitTemplate.convertAndSend(
                        reployTo, // routingKey
                        replyContent, // message
                        msg -> { // postProcessMessage
                            msg.getMessageProperties().setCorrelationId(correlationId);
                            return msg;
                        });
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("[onMessage][线程编号:{} 消息内容：{}], end", Thread.currentThread().getId(), message);
    }

}
