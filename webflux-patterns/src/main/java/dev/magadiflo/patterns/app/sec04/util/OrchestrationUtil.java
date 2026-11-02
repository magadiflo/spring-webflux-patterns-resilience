package dev.magadiflo.patterns.app.sec04.util;

import dev.magadiflo.patterns.app.sec04.dto.InventoryRequest;
import dev.magadiflo.patterns.app.sec04.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec04.dto.PaymentRequest;
import dev.magadiflo.patterns.app.sec04.dto.ShippingRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrchestrationUtil {

    public static void buildRequestContext(OrchestrationRequestContext context) {
        buildPaymentRequest(context);
        buildInventoryRequest(context);
        buildShippingRequest(context);
    }

    private static void buildPaymentRequest(OrchestrationRequestContext context) {
        var paymentRequest = new PaymentRequest(
                context.getOrderRequest().userId(),
                context.getProductPrice() * context.getOrderRequest().quantity(),
                context.getOrderId()
        );
        context.setPaymentRequest(paymentRequest);
    }

    private static void buildInventoryRequest(OrchestrationRequestContext context) {
        var inventoryRequest = new InventoryRequest(
                context.getOrderId(),
                context.getOrderRequest().productId(),
                context.getOrderRequest().quantity()
        );
        context.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(OrchestrationRequestContext context) {
        var shippingRequest = new ShippingRequest(
                context.getOrderRequest().quantity(),
                context.getOrderRequest().userId(),
                context.getOrderId()
        );
        context.setShippingRequest(shippingRequest);
    }
}
