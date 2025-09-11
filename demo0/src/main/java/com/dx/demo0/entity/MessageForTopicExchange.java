package com.dx.demo0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageForTopicExchange {
    public static final String QUEUE_NAME = "QUEUE1";
    public static final String EXCHANGE_NAME = "EXCHANGE1";
    public static final String ROUTING_KEY = "order.#";

    private String message;
}
