package br.com.microservices.order.manager.service.impl;

import br.com.microservices.order.manager.controller.request.CreateOrderRequest;
import br.com.microservices.order.manager.controller.response.OrderResponse;
import br.com.microservices.order.manager.event.OrderRequestedEvent;
import br.com.microservices.order.manager.repository.OrderRepository;
import br.com.microservices.order.manager.repository.entity.Order;
import br.com.microservices.order.manager.service.OrderService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private KafkaTemplate<String, OrderRequestedEvent> template;

    public OrderServiceImpl(OrderRepository orderRepository,
            KafkaTemplate<String, OrderRequestedEvent> template) {
        this.orderRepository = orderRepository;
        this.template = template;
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


        OrderRequestedEvent event = new OrderRequestedEvent(order.getId(), order.getCustomerId(), order.getAmount());
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
}
