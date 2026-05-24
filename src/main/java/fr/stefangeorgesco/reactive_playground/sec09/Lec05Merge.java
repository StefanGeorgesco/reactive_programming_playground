package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SuppressWarnings("unused")
public class Lec05Merge {

    public static void main(String[] args) {

        demo2();

        Util.sleep(Duration.ofSeconds(1));
    }

    private static void demo1() {
        Flux.merge(producer1(), producer2(), producer3())
                .take(2)
                .subscribe(Util.subscriber("merge subscriber"));
    }

    private static void demo2() {
        producer1()
                .mergeWith(producer2())
                .mergeWith(producer3())
                .take(2)
                .subscribe(Util.subscriber("merge subscriber"));
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .transform(Util.fluxLogger("producer 1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .transform(Util.fluxLogger("producer 2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3() {
        return Flux.just(11, 12, 13)
                .transform(Util.fluxLogger("producer 3"))
                .delayElements(Duration.ofMillis(10));
    }
}
