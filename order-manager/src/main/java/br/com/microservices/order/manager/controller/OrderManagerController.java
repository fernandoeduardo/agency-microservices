package br.com.microservices.order.manager.controller;

import br.com.microservices.order.manager.controller.request.CreateOrderRequest;
import br.com.microservices.order.manager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderManagerController {

    private OrderService orderService;

    @Autowired
    public OrderManagerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody CreateOrderRequest request) {
        return this.orderService.save(request);
    }

}
