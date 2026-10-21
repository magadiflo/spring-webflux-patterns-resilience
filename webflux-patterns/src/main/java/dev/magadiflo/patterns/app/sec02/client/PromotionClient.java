package dev.magadiflo.patterns.app.sec02.client;

import dev.magadiflo.patterns.app.sec02.dto.PromotionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PromotionClient {

    private final PromotionResponse noPromotion = PromotionResponse.noPromotion();
    private final WebClient webClient;

    public PromotionClient(@Value("${sec01.promotion.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PromotionResponse> getPromotion(Integer id) {
        return this.webClient
                .get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(PromotionResponse.class)
                .onErrorReturn(this.noPromotion);
    }
}
