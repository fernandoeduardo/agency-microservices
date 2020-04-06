package br.com.microservices.order.manager.config;

import br.com.microservices.order.manager.api.TicketInventoryApi;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    private Client client;

    public FeignConfiguration(Client client) {
        this.client = client;
    }

    @Bean
    public TicketInventoryApi ticketInventoryApi() {
        return Feign.builder()
                .contract(new SpringMvcContract())
                .client(client)
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(TicketInventoryApi.class))
                .logLevel(Logger.Level.FULL)
                .target(TicketInventoryApi.class, "http://localhost:8083");
    }


}
