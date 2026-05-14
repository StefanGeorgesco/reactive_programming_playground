package fr.stefangeorgesco.reactive_playground.sec04;

/*
    Take is similar to java stream's limit
 */

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec05TakeOperator {
    private static final String SUBSCRIBER_NAME = "Range subscriber";

    public static void main(String[] args) {
        take();
        takeWhile();
        takeUntil();
    }

    private static void take() {
        Flux.range(1, 10)
                .log("range > take")
                .take(3)
                .log("take > subscriber")
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
    }

    private static void takeWhile() {
        Flux.range(1, 10)
                .log("range > takeWhile")
                .takeWhile(i -> i < 4)
                .log("takeWhile > subscriber")
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
    }

    private static void takeUntil() {
        Flux.range(1, 10)
                .log("range > takeUntil")
                .takeUntil(i -> i == 3)
                .log("takeUntil > subscriber")
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
    }
}
