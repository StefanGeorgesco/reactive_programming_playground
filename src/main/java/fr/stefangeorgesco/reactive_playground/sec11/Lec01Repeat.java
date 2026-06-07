package fr.stefangeorgesco.reactive_playground.sec11;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class Lec01Repeat {

    private static final String SUBSCRIBER_NAME = "country subscriber";

    public static void main(String[] args) {
        demo5();
    }

    private static void demo1() {
        getCountryName()
                .repeat(3)
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
    }

    private static void demo2() {
        getCountryName()
                .repeat()
                .takeUntil(country -> country.equalsIgnoreCase("France"))
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
    }

    private static void demo3() {
        var atomicInteger = new AtomicInteger(0);
        getCountryName()
                .repeat(() -> atomicInteger.getAndIncrement() < 3)
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
    }

    private static void demo4() {
        getCountryName()
                .repeatWhen(longFlux -> longFlux.delayElements(Duration.ofSeconds(1)).take(3))
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));

        Util.sleep(Duration.ofSeconds(5));
    }

    private static void demo5() {
        Flux.just("A", "B", "C")
                .repeatWhen(longFlux -> longFlux.delayElements(Duration.ofSeconds(1)).take(3))
                .subscribe(Util.subscriber("letter subscriber"));

        Util.sleep(Duration.ofSeconds(5));
    }

    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> Util.faker().country().name());
    }
}
