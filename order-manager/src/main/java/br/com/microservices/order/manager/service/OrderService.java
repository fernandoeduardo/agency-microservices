package br.com.microservices.order.manager.service;

import br.com.microservices.order.manager.controller.request.CreateOrderRequest;
import br.com.microservices.order.manager.controller.response.OrderResponse;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    String save(CreateOrderRequest request);

    List<OrderResponse> findAll();

    @KafkaListener(topics = "processed-orders", groupId = "order-group-id")
    void processedOrdersListener(String message) throws IOException;
}
