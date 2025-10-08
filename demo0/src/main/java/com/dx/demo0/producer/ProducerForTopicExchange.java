package com.dx.demo0.producer;

import com.dx.demo0.entity.MessageForTopicExchange;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.RabbitFuture;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProducerForTopicExchange {
    private final RabbitTemplate rabbitTemplate;
    private final AsyncRabbitTemplate asyncRabbitTemplate;

    public void syncSend(String msg) {
        var message = new MessageForTopicExchange();
        message.setMessage(msg);
        // 同步发送消息，阻塞式，返回条件为消息发送到交换机
        rabbitTemplate.convertAndSend(
                MessageForTopicExchange.EXCHANGE_NAME, // exchange
                MessageForTopicExchange.ROUTING_KEY, // routingKey
                message); // message
    }

    public RabbitFuture<Object> asyncSend(String msg, String correlationId) {
        var message = new MessageForTopicExchange();
        message.setMessage(msg);

        CorrelationData correlationData = new CorrelationData(correlationId);

        MessagePostProcessor messagePostProcessor =  m-> {
            // 设置correlationId到消息属性中
            m.getMessageProperties().setCorrelationId(correlationId);
            // 可以在这里设置其他消息属性，如优先级、过期时间等
            return m;
        };

        // 异步发送消息，非阻塞式，返回条件为消费完成并收到回复
        return asyncRabbitTemplate.convertSendAndReceive(
                MessageForTopicExchange.EXCHANGE_NAME, // exchange
                MessageForTopicExchange.ROUTING_KEY, // routingKey
                message,
                messagePostProcessor); // message
    }
}
