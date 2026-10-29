package dev.magadiflo.patterns.app.sec04.client;

import dev.magadiflo.patterns.app.sec04.dto.PaymentRequest;
import dev.magadiflo.patterns.app.sec04.dto.PaymentResponse;
import dev.magadiflo.patterns.app.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private static final String DEDUCT = "deduct";
    private static final String REFUND = "refund";
    private final WebClient webClient;

    public UserClient(@Value("${sec03.user.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest paymentRequest) {
        return this.callUserService(DEDUCT, paymentRequest);
    }

    public Mono<PaymentResponse> refund(PaymentRequest paymentRequest) {
        return this.callUserService(REFUND, paymentRequest);
    }

    private Mono<PaymentResponse> callUserService(String endpoint, PaymentRequest request) {
        return this.webClient
                .post()
                .uri("/{endpoint}", endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private PaymentResponse buildErrorResponse(PaymentRequest request) {
        return new PaymentResponse(
                request.userId(),
                null,
                request.amount(),
                Status.FAILED
        );
    }
}
