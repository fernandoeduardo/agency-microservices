package br.com.microservices.order.manager.event;

import java.math.BigDecimal;

public class OrderRequestedEvent {

    private String orderId;
    private String customerId;
    private BigDecimal amount;

    public OrderRequestedEvent() {
    }

    public OrderRequestedEvent(String orderId, String customerId, BigDecimal amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
