package com.dx.demo0.config;

import com.dx.demo0.entity.MessageForDirectExchange;
import com.dx.demo0.entity.MessageForFanoutExchange;
import com.dx.demo0.entity.MessageForTopicExchange;
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

        // 可选：配置确认回调和返回回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息已成功到达交换机: " + (correlationData != null ? correlationData.getId() : "未知ID"));
            } else {
                System.err.println("消息到达交换机失败，原因: " + cause);
            }
        });

        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.err.println("消息路由失败: " +
                    "交换机=" + returnedMessage.getExchange() +
                    ", 路由键=" + returnedMessage.getRoutingKey() +
                    ", 消息=" + returnedMessage.getMessage() +
                    ", 原因=" + returnedMessage.getReplyText());
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

    /*
    每一类Exchange的演示都通过单独的配置类来声明
    Tips：1. 配置类及其内部静态类中的@Bean注解，都可以被Spring Boot扫描到
          2. 我们只是声明了这些Queue、Exchange、Binding，RabbitAdmin 初始化的时候会从 spring 容器
             里取出所有的交换器 bean, 队列 bean, Binding Bean然后创建到RabbitMQ中
     */

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
