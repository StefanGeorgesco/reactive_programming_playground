package fr.stefangeorgesco.reactive_playground.sec13.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    private RateLimiter() {
    }

    private static final Map<String, Integer> categoryAttempts = Collections.synchronizedMap(new HashMap<>());

    static {
        refresh();
    }

    static <T> Mono<T> limitCalls() {
        return Mono.deferContextual(ctx -> {
            boolean allowCall = ctx.<String>getOrEmpty("category")
                    .map(RateLimiter::canAllow)
                    .orElse(false);
            if (allowCall) {
                return Mono.empty();
            } else {
                return Mono.error(
                        new RuntimeException("Rate limit exceeded for category: "
                                + ctx.getOrEmpty("category").orElse("unknown")));
            }
        });
    }

    private static synchronized boolean canAllow(String category) {
        Integer attempts = categoryAttempts.getOrDefault(category, 0);
        if (attempts > 0) {
            categoryAttempts.put(category, attempts - 1);
            return true;
        }
        return false;
    }

    private static void refresh() {
        Flux.interval(Duration.ofSeconds(5))
                .startWith(0L)
                .subscribe(tick -> {
                    categoryAttempts.put("standard", 2);
                    categoryAttempts.put("prime", 3);
                });
    }
}
