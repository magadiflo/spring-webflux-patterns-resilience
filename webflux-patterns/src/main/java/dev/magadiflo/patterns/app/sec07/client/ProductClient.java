package dev.magadiflo.patterns.app.sec07.client;

import dev.magadiflo.patterns.app.sec07.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${sec06.product.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return this.webClient
                .get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(throwable -> {
                    log.warn("Error obteniendo producto con ID {}: {}", id, throwable.getMessage());
                    return Mono.empty();
                });
    }
}
