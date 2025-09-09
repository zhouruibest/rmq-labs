package com.dx.demo0.producer;

import com.dx.demo0.entity.MessageForTopicExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.RabbitFuture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerForTopicExchange {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AsyncRabbitTemplate asyncRabbitTemplate;

    public void syncSend(String msg) {
        var message = new MessageForTopicExchange();
        message.setMessage(msg);
        // 同步发送消息，阻塞式，返回条件为消息发送到交换机
        rabbitTemplate.convertAndSend(
                MessageForTopicExchange.EXCHANGE_NAME, // exchange
                MessageForTopicExchange.ROUTING_KEY, // routingKey
                message); // message
    }

    public RabbitFuture<Object> asyncSend(String msg) {
        var message = new MessageForTopicExchange();
        message.setMessage(msg);
        // 异步发送消息，非阻塞式，返回条件为消费完成并收到回复
        return asyncRabbitTemplate.convertSendAndReceive(
                MessageForTopicExchange.EXCHANGE_NAME, // exchange
                MessageForTopicExchange.ROUTING_KEY, // routingKey
                message); // message
    }
}
