package br.com.microservices.payment.processor.event;

public class PaymentProcessedEvent {

    private String ticketId;

    private boolean isPaymentApproved;

    public PaymentProcessedEvent() {
    }

    public PaymentProcessedEvent(String ticketId, boolean isPaymentApproved) {
        this.ticketId = ticketId;
        this.isPaymentApproved = isPaymentApproved;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isPaymentApproved() {
        return isPaymentApproved;
    }

    public void setPaymentApproved(boolean paymentApproved) {
        isPaymentApproved = paymentApproved;
    }
}
