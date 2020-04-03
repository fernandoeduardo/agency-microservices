package br.com.microservices.payment.processor.service;

import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public interface PaymentService {
    @KafkaListener(topics = "requested-payments", groupId = "ticket-group-id")
    void requestedPaymentsListener(String message) throws IOException;
}
