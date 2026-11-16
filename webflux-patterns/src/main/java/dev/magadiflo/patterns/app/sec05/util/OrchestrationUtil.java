package dev.magadiflo.patterns.app.sec05.util;

import dev.magadiflo.patterns.app.sec05.dto.InventoryRequest;
import dev.magadiflo.patterns.app.sec05.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec05.dto.PaymentRequest;
import dev.magadiflo.patterns.app.sec05.dto.ShippingRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrchestrationUtil {

    public static void buildPaymentRequest(OrchestrationRequestContext context) {
        var paymentRequest = new PaymentRequest(
                context.getOrderRequest().userId(),
                context.getProductPrice() * context.getOrderRequest().quantity(),
                context.getOrderId()
        );
        context.setPaymentRequest(paymentRequest);
    }

    public static void buildInventoryRequest(OrchestrationRequestContext context) {
        var inventoryRequest = new InventoryRequest(
                context.getPaymentResponse().paymentId(),
                context.getOrderRequest().productId(),
                context.getOrderRequest().quantity()
        );
        context.setInventoryRequest(inventoryRequest);
    }

    public static void buildShippingRequest(OrchestrationRequestContext context) {
        var shippingRequest = new ShippingRequest(
                context.getOrderRequest().quantity(),
                context.getOrderRequest().userId(),
                context.getInventoryResponse().inventoryId()
        );
        context.setShippingRequest(shippingRequest);
    }
}
