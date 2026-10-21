package dev.magadiflo.patterns.app.sec02.client;

import dev.magadiflo.patterns.app.sec02.dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class ReviewClient {

    private final WebClient webClient;

    public ReviewClient(@Value("${sec01.review.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<List<Review>> getReview(Integer id) {
        return this.webClient
                .get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .onErrorReturn(Collections.emptyList());
    }
}
