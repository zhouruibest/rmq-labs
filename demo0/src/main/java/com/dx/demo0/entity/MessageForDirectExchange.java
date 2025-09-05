package com.dx.demo0.entity;

import java.io.Serializable;

// 因为 RabbitTemplate 默认使用 Java 自带的序列化方式，进行序列化 POJO 类型的消息
import lombok.Data;

@Data
public class MessageForDirectExchange implements Serializable {
    public static final String QUEUE_NAME = "QUEUE0";
    public static final String EXCHANGE_NAME = "EXCHANGE0";
    public static final String ROUTING_KEY = "ROUTING_KEY0";

    private String message;

    @Override
    public String toString() {
        return "Message: " + message;
    }
}
