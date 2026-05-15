package fr.stefangeorgesco.reactive_playground.sec05;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

/*
    Similar to error handling.
    Handling empty!
 */

public class Lec08SwitchIfEmpty {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .filter(i -> i > 10)
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber("SwitchIfEmpty subscriber"));
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 3);
    }
}
