package br.com.microservices.payment.processor.event;

import java.math.BigDecimal;

public class PaymentRequestedEvent {

    private String ticketId;
    private String customerId;
    private BigDecimal amount;
    private String creditCard;

    public PaymentRequestedEvent() {
    }

    public PaymentRequestedEvent(String ticketId, String customerId, BigDecimal amount, String creditCard) {
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.amount = amount;
        this.creditCard = creditCard;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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
