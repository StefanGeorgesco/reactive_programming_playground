package fr.stefangeorgesco.reactive_playground.sec06.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RevenueService implements OrderProcessor {

    private final Map<String, Integer> revenuePerCategory;

    public RevenueService() {
        this.revenuePerCategory = new HashMap<>();
    }

    @Override
    public void consume(Order order) {
        revenuePerCategory.merge(order.category(), order.price(), Integer::sum);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> revenuePerCategory.toString());
    }
}
