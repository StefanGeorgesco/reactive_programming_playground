package fr.stefangeorgesco.reactive_playground.sec06.assignment;

import reactor.core.publisher.Flux;

public interface OrderService {

    /**
     * Get the stream of orders.
     *
     * @return a Flux of Order objects
     */
    Flux<Order> getOrderStream();
}
