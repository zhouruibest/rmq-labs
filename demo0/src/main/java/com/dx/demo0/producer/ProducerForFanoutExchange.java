package com.dx.demo0.producer;

import com.dx.demo0.entity.MessageForFanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerForFanoutExchange {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(String msg) {
        MessageForFanoutExchange message = new MessageForFanoutExchange();
        message.setMessage(msg);
        // 同步发送消息，阻塞式，返回条件为消息发送到交换机
        rabbitTemplate.convertAndSend(
                MessageForFanoutExchange.EXCHANGE_NAME, // exchange
                null, // routingKey
                message); // message
    }

}
