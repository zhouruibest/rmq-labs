package com.dx.demo0.config;

import com.dx.demo0.entity.MessageForDirectExchange;
import com.dx.demo0.entity.MessageForFanoutExchange;
import com.dx.demo0.entity.MessageForTopicExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    // 配置Jackson消息转换器
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 配置RabbitTemplate并设置消息转换器
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置消息转换器
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        // 监控消息是否成功到达交换机
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                logger.info("消息已成功到达交换机: {}", correlationData != null ? correlationData.getId() : "未知ID");
            } else {
                logger.error("消息到达交换机失败，原因: {}", cause);
            }
        });

        // 用于监控消息路由失败的情况
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            logger.error("消息路由失败: 交换机={}, 路由键={}, 消息={}, 原因={}", returnedMessage.getExchange(), returnedMessage.getRoutingKey(), returnedMessage.getMessage(), returnedMessage.getReplyText());
        });

        return rabbitTemplate;
    }

    // 配置AsyncRabbitTemplate并设置消息转换器
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        // AsyncRabbitTemplate基于已配置好的RabbitTemplate构建
        // 会自动使用RabbitTemplate中设置的消息转换器
        AsyncRabbitTemplate asyncTemplate = new AsyncRabbitTemplate(rabbitTemplate);
        return asyncTemplate;
    }

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        @Bean
        public Queue queue0() {
            return new Queue(MessageForDirectExchange.QUEUE_NAME,
                    true, // durable: 是否持久化到磁盘，当 RabbitMQ 重启后，仍然存在
                    false, // exclusive: 是否排它，队列只对它的连接可见
                    false); // autoDelete: 当没有消费者时，自动删除
        }

        @Bean
        public DirectExchange exchange0() {
            return new DirectExchange(MessageForDirectExchange.EXCHANGE_NAME,
                    true,  // durable: 持久化到磁盘，当 RabbitMQ 服务重启后，该交换机会保留
                    false);  // autoDelete: 当最后一个绑定到该交换机的队列/交换机被解绑后，交换机会被自动删除
        }

        @Bean
        public Binding binding0() {
            return BindingBuilder.bind(queue0()).to(exchange0()).with(MessageForDirectExchange.ROUTING_KEY);
        }
    }

    /**
     * Topic Exchange 示例的配置类
     */
    public static class TopicExchangeDemoConfiguration {

        @Bean
        public Queue queue1() {
            return new Queue(MessageForTopicExchange.QUEUE_NAME,
                    true, // durable
                    false, // exclusive
                    false); // autoDelete
        }

        @Bean
        public TopicExchange exchange1() {
            return new TopicExchange(MessageForTopicExchange.EXCHANGE_NAME,
                    true,  // durable
                    false);  // autoDelete
        }

        @Bean
        public Binding binding1() {
            return BindingBuilder.bind(queue1()).to(exchange1()).with(MessageForTopicExchange.ROUTING_KEY);
        }

    }

    /**
     * Fanout Exchange 示例的配置类
     */
    public static class FanoutExchangeDemoConfiguration {

        @Bean
        public Queue queueA() {
            return new Queue(MessageForFanoutExchange.QUEUE_NAMEA,
                    true, // durable
                    false, // exclusive
                    false); // autoDelete
        }

        @Bean
        public Queue queueB() {
            return new Queue(MessageForFanoutExchange.QUEUE_NAMEB,
                    true, // durable
                    false, // exclusive
                    false); // autoDelete
        }

        @Bean
        public FanoutExchange exchange2() {
            return new FanoutExchange(MessageForFanoutExchange.EXCHANGE_NAME,
                    true,  // durable
                    false);  // autoDelete
        }

        @Bean
        public Binding bindingA() {
            return BindingBuilder.bind(queueA()).to(exchange2());
        }

        @Bean
        public Binding bindingB() {
            return BindingBuilder.bind(queueB()).to(exchange2());
        }
    }
}
