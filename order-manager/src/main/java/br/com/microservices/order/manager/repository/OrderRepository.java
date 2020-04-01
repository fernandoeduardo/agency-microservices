package br.com.microservices.order.manager.repository;

import br.com.microservices.order.manager.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
