package br.com.microservices.ticket.inventory.service;

import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public interface TicketService {
    @KafkaListener(topics = "requested-orders", groupId = "order-group-id")
    void requestedOrdersListener(String message) throws IOException;

    @KafkaListener(topics = "processed-payment", groupId = "payment-group-id")
    void processedPaymentListener(String message) throws IOException;
}
