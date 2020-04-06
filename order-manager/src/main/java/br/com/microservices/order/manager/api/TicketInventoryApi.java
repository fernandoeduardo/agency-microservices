package br.com.microservices.order.manager.api;

import br.com.microservices.order.manager.api.response.CheckHealthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("ticket-inventory")
public interface TicketInventoryApi {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/actuator/health", consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckHealthResponse checkHealth();
}
