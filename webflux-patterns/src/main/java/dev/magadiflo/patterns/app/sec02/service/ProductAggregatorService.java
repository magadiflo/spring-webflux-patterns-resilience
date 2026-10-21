package dev.magadiflo.patterns.app.sec02.service;

import dev.magadiflo.patterns.app.sec02.client.ProductClient;
import dev.magadiflo.patterns.app.sec02.client.PromotionClient;
import dev.magadiflo.patterns.app.sec02.client.ReviewClient;
import dev.magadiflo.patterns.app.sec02.dto.Price;
import dev.magadiflo.patterns.app.sec02.dto.ProductAggregate;
import dev.magadiflo.patterns.app.sec02.dto.ProductResponse;
import dev.magadiflo.patterns.app.sec02.dto.PromotionResponse;
import dev.magadiflo.patterns.app.sec02.dto.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductAggregatorService {

    private final ProductClient productClient;
    private final PromotionClient promotionClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
        return Mono.zip(
                        this.productClient.getProduct(id),
                        this.promotionClient.getPromotion(id),
                        this.reviewClient.getReview(id)
                )
                .map(tuple ->
                        this.toProductAggregate(tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private ProductAggregate toProductAggregate(ProductResponse product, PromotionResponse promotion, List<Review> reviews) {
        Price price = this.createPrice(product, promotion);
        return new ProductAggregate(
                product.id(),
                product.category(),
                product.description(),
                price,
                reviews
        );
    }

    private Price createPrice(ProductResponse product, PromotionResponse promotion) {
        double amountSaved = product.price() * promotion.discount() / 100;
        double discountedPrice = product.price() - amountSaved;
        return new Price(
                product.price(),
                promotion.discount(),
                discountedPrice,
                amountSaved,
                promotion.endDate()
        );
    }
}
