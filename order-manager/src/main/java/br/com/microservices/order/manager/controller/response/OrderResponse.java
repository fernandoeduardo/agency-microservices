package br.com.microservices.order.manager.controller.response;

import br.com.microservices.order.manager.repository.entity.Order;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderResponse implements Serializable {

    private String id;

    private String customerId;

    private BigDecimal amount;

    private String status;

    public OrderResponse() {
    }

    public OrderResponse(String id, String customerId, BigDecimal amount, String status) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getAmount(),
                order.getStatus()
        );
    }
}
