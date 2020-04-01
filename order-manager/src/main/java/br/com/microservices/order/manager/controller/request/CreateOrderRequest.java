package br.com.microservices.order.manager.controller.request;

import java.math.BigDecimal;

public class CreateOrderRequest {

    private String customerId;

    private BigDecimal amount;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(String customerId, BigDecimal amount) {
        this.customerId = customerId;
        this.amount = amount;
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
