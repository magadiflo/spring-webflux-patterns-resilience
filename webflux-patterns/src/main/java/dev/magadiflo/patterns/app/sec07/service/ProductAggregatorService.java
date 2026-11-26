package dev.magadiflo.patterns.app.sec07.service;

import dev.magadiflo.patterns.app.sec07.client.ProductClient;
import dev.magadiflo.patterns.app.sec07.client.ReviewClient;
import dev.magadiflo.patterns.app.sec07.dto.ProductAggregate;
import dev.magadiflo.patterns.app.sec07.dto.Product;
import dev.magadiflo.patterns.app.sec07.dto.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductAggregatorService {

    private final ProductClient productClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
        return Mono.zip(
                        this.productClient.getProduct(id),
                        this.reviewClient.getReview(id)
                )
                .map(tuple ->
                        this.toProductAggregate(tuple.getT1(), tuple.getT2()));
    }

    private ProductAggregate toProductAggregate(Product product, List<Review> reviews) {
        return new ProductAggregate(
                product.id(),
                product.category(),
                product.description(),
                product.price(),
                reviews
        );
    }
}
