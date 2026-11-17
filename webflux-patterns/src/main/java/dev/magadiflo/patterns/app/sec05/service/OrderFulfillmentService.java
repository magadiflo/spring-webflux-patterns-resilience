package dev.magadiflo.patterns.app.sec05.service;

import dev.magadiflo.patterns.app.sec05.client.ProductClient;
import dev.magadiflo.patterns.app.sec05.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec05.dto.ProductResponse;
import dev.magadiflo.patterns.app.sec05.dto.Status;
import dev.magadiflo.patterns.app.sec05.service.orchestrator.InventoryOrchestrator;
import dev.magadiflo.patterns.app.sec05.service.orchestrator.PaymentOrchestrator;
import dev.magadiflo.patterns.app.sec05.service.orchestrator.ShippingOrchestrator;
import dev.magadiflo.patterns.app.sec05.util.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrderFulfillmentService {

    private final ProductClient productClient;
    private final PaymentOrchestrator paymentOrchestrator;
    private final InventoryOrchestrator inventoryOrchestrator;
    private final ShippingOrchestrator shippingOrchestrator;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext context) {
        return this.getProduct(context)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(this.paymentOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildInventoryRequest)
                .flatMap(this.inventoryOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildShippingRequest)
                .flatMap(this.shippingOrchestrator::create)
                .doOnNext(context1 -> context1.setStatus(Status.SUCCESS))
                .doOnError(throwable -> context.setStatus(Status.FAILED))
                .onErrorReturn(context);
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext context) {
        return this.productClient.getProduct(context.getOrderRequest().productId())
                .map(ProductResponse::price)
                .doOnNext(context::setProductPrice)
                .map(price -> context);
    }
}
