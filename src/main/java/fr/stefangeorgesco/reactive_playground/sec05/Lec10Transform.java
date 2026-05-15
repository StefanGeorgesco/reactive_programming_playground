package fr.stefangeorgesco.reactive_playground.sec05;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.UnaryOperator;

public class Lec10Transform {

    private static final Logger log = LoggerFactory.getLogger(Lec10Transform.class);

    record Customer(int id, String name) {
    }

    record PurchaseOrder(String productName, int price, int quantity) {
    }

    public static void main(String[] args) {

        var isLogEnabled = args.length > 0 && args[0].equalsIgnoreCase("log");

        getCustomers()
                .transform(isLogEnabled ? addLog(): UnaryOperator.identity())
                .subscribe();

        getPurchaseOrders()
                .transform(isLogEnabled ? addLog() : UnaryOperator.identity())
                .subscribe();
    }

    private static Flux<Customer> getCustomers() {
        return Flux.range(1, 3)
                .map(i -> new Customer(i, Util.faker().name().fullName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 5)
                .map(i
                                -> new PurchaseOrder(
                                Util.faker().commerce().productName(),
                                Util.faker().number().numberBetween(10, 100),
                                Util.faker().number().numberBetween(1, 10)
                        )
                );
    }

    private static <T> UnaryOperator<Flux<T>> addLog() {
        return flux -> flux
                .doOnNext(i -> log.info("received {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(err -> log.error("error", err));
    }
}
