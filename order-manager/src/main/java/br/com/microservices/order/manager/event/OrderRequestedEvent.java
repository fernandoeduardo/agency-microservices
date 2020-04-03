package br.com.microservices.order.manager.event;

import java.math.BigDecimal;

public class OrderRequestedEvent {

    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private String creditCard;

    public OrderRequestedEvent() {
    }

    public OrderRequestedEvent(String orderId, String customerId, BigDecimal amount, String creditCard) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.creditCard = creditCard;
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

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
