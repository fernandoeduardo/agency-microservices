package br.com.microservices.ticket.inventory.repository;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tickets")
public class Ticket {

    @Id
    private String id;

    private String customerId;

    private String orderId;

    private String status;

    public Ticket() {
    }

    public Ticket(String id, String customerId, String orderId, String status) {
        this.id = id;
        this.customerId = customerId;
        this.orderId = orderId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
