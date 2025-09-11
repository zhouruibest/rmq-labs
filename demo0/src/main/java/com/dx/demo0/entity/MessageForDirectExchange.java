package com.dx.demo0.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageForDirectExchange {
    public static final String QUEUE_NAME = "QUEUE0";
    public static final String EXCHANGE_NAME = "EXCHANGE0";
    public static final String ROUTING_KEY = "ROUTING_KEY0";

    private String message;

}
