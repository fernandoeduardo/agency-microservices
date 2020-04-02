package br.com.microservices.order.manager.service;

import br.com.microservices.order.manager.controller.request.CreateOrderRequest;
import br.com.microservices.order.manager.controller.response.OrderResponse;

import java.util.List;

public interface OrderService {
    String save(CreateOrderRequest request);

    List<OrderResponse> findAll();
}
