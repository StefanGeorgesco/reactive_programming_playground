package fr.stefangeorgesco.reactive_playground.sec06.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InventoryService implements OrderProcessor {

    private final Map<String, Integer> inventory;

    public InventoryService() {
        this.inventory = new HashMap<>();
    }

    @Override
    public void consume(Order order) {
        int quantity = order.quantity();
        this.inventory.compute(order.category(),
                (k, v) -> v == null ? 500 - quantity : v - quantity);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> this.inventory.toString());
    }
}
