package com.dx.demo0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageForFanoutExchange {
    public static final String QUEUE_NAMEA = "QUEUE2A";
    public static final String QUEUE_NAMEB = "QUEUE2B";
    public static final String EXCHANGE_NAME = "EXCHANGE2";

    private String message;
}
