package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {

    public static void main(String[] args) {

        Flux.range(3, 5)
                .subscribe(Util.subscriber("Range subscriber"));

        // Assignment: generate 10 random first names

        Flux.range(0, 10)
                .map(i -> Util.faker().name().firstName())
                .subscribe(Util.subscriber("First names subscriber"));
    }
}
