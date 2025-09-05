package com.dx.demo0.producer;

import com.dx.demo0.entity.MessageForTopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class ProducerForTopicExchange {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(String msg) {
        var message = new MessageForTopicExchange();
        message.setMessage(msg);
        // 同步发送消息
        rabbitTemplate.convertAndSend(
                MessageForTopicExchange.EXCHANGE_NAME, // exchange
                MessageForTopicExchange.ROUTING_KEY, // routingKey
                message); // message
    }

    public void syncSendDefault(String msg) {
        var message = new MessageForTopicExchange();
        message.setMessage(msg);
        // 同步发送消息, 使用默认交换机.默认的交换机连接了每一个队列并使用队列名作为路由键
        rabbitTemplate.convertAndSend(
                MessageForTopicExchange.ROUTING_KEY, // routingKey
                message); // message
    }

    @Async
    public ListenableFuture<Void> asyncSend() {
        try {
            // 发送消息
            this.syncSend("这是一条direct exchange的消息");
            this.syncSendDefault("这是一条direct exchange的消息, 使用默认交换机和路由键");
            // 返回成功的 Future
            return AsyncResult.forValue(null);
        } catch (Throwable ex) {
            // 返回异常的 Future
            return AsyncResult.forExecutionException(ex);
        }
    }
}
