package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec06Log {

    public static void main(String[] args) {

        Flux.range(1, 5)
                .log("range > faker")
                .map(i -> Util.faker().name().firstName())
                .log("faker > final subscriber")
                .subscribe(Util.subscriber("Range subscriber"));
    }
}
