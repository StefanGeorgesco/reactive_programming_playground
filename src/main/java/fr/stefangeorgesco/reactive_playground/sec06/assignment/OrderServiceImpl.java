package fr.stefangeorgesco.reactive_playground.sec06.assignment;

import fr.stefangeorgesco.reactive_playground.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class OrderServiceImpl extends AbstractHttpClient implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private Flux<Order> orderStream;

    @Override
    public Flux<Order> getOrderStream() {
        if (Objects.isNull(this.orderStream)) {
            this.orderStream = this.requestOrderStream();
        }
        return this.orderStream;
    }

    /*
        Private methods
     */

    /**
     * Requests a stream of orders from the server.
     *
     * @return a Flux of Order objects
     */
    private Flux<Order> requestOrderStream() {
        return this.httpClient.get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(this::parseOrder)
                .doOnNext(order -> log.info("{}", order))
                .publish()
                .autoConnect(2);
    }

    /**
     * Parses a string into an Order object.
     *
     * @param orderString the string to parse in the format "item:category:price:quantity"
     * @return an Order object
     */
    private Order parseOrder(String orderString) {
        String[] parts = orderString.split(":");
        return new Order(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }
}
