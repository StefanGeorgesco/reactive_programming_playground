package fr.stefangeorgesco.reactive_playground.sec09.applications;

import reactor.core.publisher.Mono;

import java.util.Map;

public class PaymentService {

    private PaymentService() {}

    private static final Map<Integer, Integer> userBalanceTable = Map.of(
            1, 100,
            2, 200,
            3, 300
    );

    public static Mono<Integer> getUserBalance(Integer userId) {
        return Mono.justOrEmpty(userBalanceTable.get(userId));
    }
}
