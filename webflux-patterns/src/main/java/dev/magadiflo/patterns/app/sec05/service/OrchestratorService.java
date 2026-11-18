package dev.magadiflo.patterns.app.sec05.service;

import dev.magadiflo.patterns.app.sec05.dto.Address;
import dev.magadiflo.patterns.app.sec05.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec05.dto.OrderRequest;
import dev.magadiflo.patterns.app.sec05.dto.OrderResponse;
import dev.magadiflo.patterns.app.sec05.dto.Status;
import dev.magadiflo.patterns.app.sec05.util.DebugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrchestratorService {

    private final OrderFulfillmentService fulfillmentService;
    private final OrderCancellationService cancellationService;

    private final DebugUtil debugUtil;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> orderRequestMono) {
        return orderRequestMono
                .map(OrchestrationRequestContext::new)
                .flatMap(this.fulfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                .doOnNext(this.debugUtil::print) // solo para debugging
                .map(this::toOrderResponse);
    }

    private void doOrderPostProcessing(OrchestrationRequestContext context) {
        if (context.getStatus() == Status.FAILED) {
            this.cancellationService.cancelOrder(context);
        }
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext context) {
        boolean isSuccess = context.getStatus() == Status.SUCCESS;
        Address address = isSuccess ? context.getShippingResponse().address() : null;
        String deliveryDate = isSuccess ? context.getShippingResponse().expectedDelivery() : null;

        return new OrderResponse(
                context.getOrderRequest().userId(),
                context.getOrderRequest().productId(),
                context.getOrderId(),
                context.getStatus(),
                address,
                deliveryDate
        );
    }
}
