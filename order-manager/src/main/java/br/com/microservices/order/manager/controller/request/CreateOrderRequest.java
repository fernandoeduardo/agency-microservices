package br.com.microservices.order.manager.controller.request;

import java.math.BigDecimal;

public class CreateOrderRequest {

    private String customerId;

    private BigDecimal amount;

    private String creditCard;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(String customerId, BigDecimal amount, String creditCard) {
        this.customerId = customerId;
        this.amount = amount;
        this.creditCard = creditCard;
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
