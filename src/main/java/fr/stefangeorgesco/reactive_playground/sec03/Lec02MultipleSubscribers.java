package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {

    public static void main(String[] args) {

        var flux = Flux.just(1, 2, 3, 4, 5, 6);

        flux.subscribe(Util.subscriber("Subscriber 1"));
        flux
                .filter(i -> i % 2 == 0)
                .map(i -> "even - " + i)
                .subscribe(Util.subscriber("Even Subscriber"));
        flux
                .filter(i -> i % 2 != 0)
                .map(i -> "odd - " + i)
                .subscribe(Util.subscriber("Odd Subscriber"));
        flux
                .filter(i -> i > 3)
                .map(i -> 2 * i)
                .subscribe(Util.subscriber("Special Subscriber"));
        flux
                .filter(i -> i > 7)
                .subscribe(Util.subscriber("Big Subscriber"));
    }
}
