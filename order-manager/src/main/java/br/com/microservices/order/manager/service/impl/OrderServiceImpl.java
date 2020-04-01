package br.com.microservices.order.manager.service.impl;

import br.com.microservices.order.manager.repository.OrderRepository;
import br.com.microservices.order.manager.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
}
