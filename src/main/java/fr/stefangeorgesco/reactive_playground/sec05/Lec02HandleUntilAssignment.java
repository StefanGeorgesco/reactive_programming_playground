package fr.stefangeorgesco.reactive_playground.sec05;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssignment {

    public static void main(String[] args) {
        Flux.<String>generate(synchronousSink
                        -> synchronousSink.next(Util.faker().country().name()))
                .<String>handle((country, synchronousSink) -> {
                    synchronousSink.next(country);
                    if (country.equalsIgnoreCase("France")) {
                        synchronousSink.complete();
                    }
                })
                .subscribe(Util.subscriber("Subscriber"));
    }
}
