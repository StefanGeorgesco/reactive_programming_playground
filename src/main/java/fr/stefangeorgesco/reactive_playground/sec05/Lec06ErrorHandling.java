package fr.stefangeorgesco.reactive_playground.sec05;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    How to handle error in a reactive pipeline
    Flux.(...)
        ...
        ...
        ...
        ...
 */

public class Lec06ErrorHandling {

    private static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {
        onErrorReturn();
        onErrorResume();
        onErrorComplete();
        onErrorContinue();
    }

    private static void onErrorReturn() {
        log.info("### onErrorReturn demo ###");
        Flux.range(1, 10)
                .map(i -> i / (5 - i))
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber("on error return subscriber"));
    }

    private static void onErrorResume() {
        log.info("### onErrorResume demo ###");
        Flux.range(1, 10)
                .map(i -> i / (5 - i))
                .onErrorResume(ArithmeticException.class, e -> fallback1())
                .onErrorResume(e -> fallback2())
                .onErrorReturn(-3)
                .subscribe(Util.subscriber("on error resume subscriber"));
    }

    private static void onErrorComplete() {
        log.info("### onErrorComplete demo ###");
        Flux.range(1, 10)
                .map(i -> i / (5 - i))
                .onErrorComplete()
                .subscribe(Util.subscriber("on error complete subscriber"));
    }

    private static void onErrorContinue() {
        log.info("### onErrorContinue demo ###");
        Flux.range(1, 10)
                .map(i -> i / (5 - i))
                .onErrorContinue(ArithmeticException.class,
                        (e, i) -> log.error("arithmetic error on element {}", i))
                .onErrorContinue(((e, i) -> log.error("other error on element {}", i)))
                .subscribe(Util.subscriber("on error continue subscriber"));
    }

    private static Mono<Integer> fallback1() {
        log.info("fallback 1 was called");
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }

    private static Mono<Integer> fallback2() {
        log.info("fallback 2 was called");
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000));
    }
}
