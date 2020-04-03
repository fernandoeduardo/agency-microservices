package br.com.microservices.order.manager.service.impl;

import br.com.microservices.order.manager.controller.request.CreateOrderRequest;
import br.com.microservices.order.manager.controller.response.OrderResponse;
import br.com.microservices.order.manager.event.OrderProcessedEvent;
import br.com.microservices.order.manager.event.OrderRequestedEvent;
import br.com.microservices.order.manager.repository.OrderRepository;
import br.com.microservices.order.manager.repository.entity.Order;
import br.com.microservices.order.manager.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private ObjectMapper objectMapper;
    private OrderRepository orderRepository;
    private KafkaTemplate<String, Object> template;

    public OrderServiceImpl(OrderRepository orderRepository,
            KafkaTemplate<String, Object> template,
            ObjectMapper objectMapper
    ) {
        this.orderRepository = orderRepository;
        this.template = template;
        this.objectMapper = objectMapper;
    }

    @Override
    @CacheEvict(cacheNames = "Orders", allEntries = true)
    public String save(CreateOrderRequest request) {

        Order order = new Order(
                UUID.randomUUID().toString(),
                request.getCustomerId(),
                request.getAmount(),
                "REQUESTED");

        orderRepository.save(order);


        OrderRequestedEvent event = new OrderRequestedEvent(order.getId(), order.getCustomerId(), order.getAmount(),
                request.getCreditCard());

        template.send("requested-orders", event);

        return order.getId();
    }

    @Override
    @Cacheable(cacheNames = "Orders", key = "#root.method.name")
    public List<OrderResponse> findAll() {
        return this.orderRepository.findAll()
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @KafkaListener(topics = "processed-orders", groupId = "order-group-id")
    public void processedOrdersListener(String message) throws IOException {

        OrderProcessedEvent orderProcessedEvent = this.objectMapper.readValue(message, OrderProcessedEvent.class);

        Optional<Order> optionalOrder = orderRepository.findById(orderProcessedEvent.getOrderId());

        if(optionalOrder.get() != null) {
            Order order = optionalOrder.get();
            order.setStatus(orderProcessedEvent.getStatus());
            orderRepository.save(order);
        }

    }
}
