package br.com.microservices.ticket.inventory.service.impl;

import br.com.microservices.ticket.inventory.event.OrderProcessedEvent;
import br.com.microservices.ticket.inventory.event.OrderRequestedEvent;
import br.com.microservices.ticket.inventory.event.PaymentProcessedEvent;
import br.com.microservices.ticket.inventory.event.PaymentRequestedEvent;
import br.com.microservices.ticket.inventory.repository.Ticket;
import br.com.microservices.ticket.inventory.repository.TicketRepository;
import br.com.microservices.ticket.inventory.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
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

        Ticket ticket = saveTicket(orderRequestedEvent);

        producePaymentRequest(ticket.getId(), orderRequestedEvent);
    }

    @Override
    @KafkaListener(topics = "processed-payment", groupId = "payment-group-id")
    public void processedPaymentListener(String message) throws IOException {

        PaymentProcessedEvent paymentProcessedEvent = this.objectMapper.readValue(message, PaymentProcessedEvent.class);

        Optional<Ticket> optionalTicket = ticketRepository.findById(paymentProcessedEvent.getTicketId());


        if (optionalTicket.get() != null) {

            Ticket ticket = optionalTicket.get();

            if (paymentProcessedEvent.isPaymentApproved()) {
                ticket.setStatus("APPROVED");
            } else {
                ticket.setStatus("REPROVED");
            }

            ticketRepository.save(ticket);

            OrderProcessedEvent orderProcessedEvent = new OrderProcessedEvent(ticket.getOrderId(), ticket.getStatus());
            template.send("processed-orders", orderProcessedEvent);
        }
    }

    private void producePaymentRequest(String ticketId, OrderRequestedEvent orderRequestedEvent) {
        PaymentRequestedEvent paymentRequestedEvent = new PaymentRequestedEvent(ticketId,
                orderRequestedEvent.getCustomerId(),
                orderRequestedEvent.getAmount(), orderRequestedEvent.getCreditCard());

        template.send("requested-payments", paymentRequestedEvent);
    }

    private Ticket saveTicket(OrderRequestedEvent orderRequestedEvent) {
        Ticket ticket = new Ticket(UUID.randomUUID().toString(), orderRequestedEvent.getCustomerId(),
                orderRequestedEvent.getOrderId(), "UNPAID");

        ticketRepository.save(ticket);

        return ticket;
    }


}
