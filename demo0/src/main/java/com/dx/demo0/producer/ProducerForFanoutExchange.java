package com.dx.demo0.producer;

import com.dx.demo0.entity.MessageForFanoutExchange;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProducerForFanoutExchange {
    private final RabbitTemplate rabbitTemplate;

    public void syncSend(String msg) {
        MessageForFanoutExchange message = new MessageForFanoutExchange();
        message.setMessage(msg);
        // 同步发送消息，阻塞式，返回条件为消息发送到交换机
        rabbitTemplate.convertAndSend(
                MessageForFanoutExchange.EXCHANGE_NAME, // exchange
                "", // routingKey
                message); // message
    }

}
