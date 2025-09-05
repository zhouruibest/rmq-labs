package com.dx.demo0.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageForFanoutExchange implements Serializable {
    public static final String QUEUE_NAMEA = "QUEUE2A";
    public static final String QUEUE_NAMEB = "QUEUE2B";
    public static final String EXCHANGE_NAME = "EXCHANGE2";

    private String message;

    @Override
    public String toString() {
        return "Message: " + message;
    }
}
