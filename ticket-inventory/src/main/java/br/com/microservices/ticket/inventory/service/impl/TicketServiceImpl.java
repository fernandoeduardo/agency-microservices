package br.com.microservices.ticket.inventory.service.impl;

import br.com.microservices.ticket.inventory.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TicketServiceImpl implements TicketService {

    private ObjectMapper objectMapper;

    public TicketServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "requested-orders", groupId = "order-group-id")
    public void requestedOrdersListener(String message) throws IOException {

        System.out.println(message);

    }


}
