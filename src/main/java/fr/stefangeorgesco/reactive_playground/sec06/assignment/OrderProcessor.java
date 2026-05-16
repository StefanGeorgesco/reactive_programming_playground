package fr.stefangeorgesco.reactive_playground.sec06.assignment;

import reactor.core.publisher.Flux;

public interface OrderProcessor {

    /**
     * Consume an order.
     *
     * @param order the order to consume
     */
    void consume(Order order);

    /**
     * Stream the current state of the inventory.
     *
     * @return a Flux of inventory state strings
     */
    Flux<String> stream();
}
