package br.com.microservices.order.manager.service.impl;

import br.com.microservices.order.manager.controller.request.CreateOrderRequest;
import br.com.microservices.order.manager.event.OrderRequestedEvent;
import br.com.microservices.order.manager.repository.OrderRepository;
import br.com.microservices.order.manager.repository.entity.Order;
import br.com.microservices.order.manager.service.OrderService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

}
