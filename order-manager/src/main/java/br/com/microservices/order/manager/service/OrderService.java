package br.com.microservices.order.manager.service;

import br.com.microservices.order.manager.controller.request.CreateOrderRequest;

public interface OrderService {
    String save(CreateOrderRequest request);
}
