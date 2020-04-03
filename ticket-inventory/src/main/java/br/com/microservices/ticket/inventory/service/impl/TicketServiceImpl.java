package br.com.microservices.ticket.inventory.service.impl;

import br.com.microservices.ticket.inventory.event.OrderRequestedEvent;
import br.com.microservices.ticket.inventory.event.PaymentRequestedEvent;
import br.com.microservices.ticket.inventory.repository.Ticket;
import br.com.microservices.ticket.inventory.repository.TicketRepository;
import br.com.microservices.ticket.inventory.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private ObjectMapper objectMapper;
    private TicketRepository ticketRepository;
    private KafkaTemplate<String, Object> template;

    public TicketServiceImpl(ObjectMapper objectMapper,
            TicketRepository ticketRepository,
            KafkaTemplate<String, Object> template) {

        this.objectMapper = objectMapper;
        this.ticketRepository = ticketRepository;
        this.template = template;

    }


    @Override
    @KafkaListener(topics = "requested-orders", groupId = "order-group-id")
    public void requestedOrdersListener(String message) throws IOException {

        OrderRequestedEvent orderRequestedEvent = this.objectMapper.readValue(message, OrderRequestedEvent.class);

        saveTicket(orderRequestedEvent);

        producePaymentRequest(orderRequestedEvent);
    }

    private void producePaymentRequest(OrderRequestedEvent orderRequestedEvent) {
        PaymentRequestedEvent paymentRequestedEvent = new PaymentRequestedEvent(orderRequestedEvent.getCustomerId(),
                orderRequestedEvent.getAmount(), orderRequestedEvent.getCreditCard());

        template.send("requested-payments", paymentRequestedEvent);
    }

    private void saveTicket(OrderRequestedEvent orderRequestedEvent) {
        Ticket ticket = new Ticket(UUID.randomUUID().toString(), orderRequestedEvent.getCustomerId(),
                orderRequestedEvent.getOrderId(), "UNPAID");

        ticketRepository.save(ticket);
    }


}
