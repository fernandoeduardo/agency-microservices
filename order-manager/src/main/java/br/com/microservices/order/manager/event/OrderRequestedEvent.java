package br.com.microservices.order.manager.event;

import java.math.BigDecimal;

public class OrderRequestedEvent {

    private String orderId;
    private String customerId;
    private BigDecimal amount;


}
