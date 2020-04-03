package br.com.microservices.payment.processor.service.impl;

import br.com.microservices.payment.processor.event.PaymentProcessedEvent;
import br.com.microservices.payment.processor.event.PaymentRequestedEvent;
import br.com.microservices.payment.processor.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {

    private ObjectMapper objectMapper;
    private KafkaTemplate<String, Object> template;

    public PaymentServiceImpl(ObjectMapper objectMapper,
            KafkaTemplate<String, Object> template) {

        this.objectMapper = objectMapper;
        this.template = template;
    }

    @Override
    @KafkaListener(topics = "requested-payments", groupId = "ticket-group-id")
    public void requestedPaymentsListener(String message) throws IOException {

        PaymentRequestedEvent paymentRequestedEvent = this.objectMapper.readValue(message, PaymentRequestedEvent.class);

        boolean isPaymentApproved = new Random().nextBoolean();

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(paymentRequestedEvent.getTicketId(),
                isPaymentApproved);

        template.send("processed-payment", paymentProcessedEvent);
    }


}
