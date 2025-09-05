package com.dx.demo0.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageForTopicExchange implements Serializable {
    public static final String QUEUE_NAME = "QUEUE1";
    public static final String EXCHANGE_NAME = "EXCHANGE1";
    public static final String ROUTING_KEY = "ROUTING_KEY1";

    private String message;

    @Override
    public String toString() {
        return "Message: " + message;
    }
}
