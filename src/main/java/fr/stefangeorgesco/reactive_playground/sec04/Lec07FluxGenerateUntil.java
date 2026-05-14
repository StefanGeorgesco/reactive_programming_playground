package fr.stefangeorgesco.reactive_playground.sec04;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {

    public static void main(String[] args) {
        demo1();
        demo2();
    }

    private static void demo1() {
        Flux.generate(synchronousSink -> {
                    var country = Util.faker().country().name();
                    synchronousSink.next(country);
                    if (country.equalsIgnoreCase("France")) {
                        synchronousSink.complete();
                    }
                })
                .subscribe(Util.subscriber("Subscriber 1"));
    }

    private static void demo2() {
        Flux.<String>generate(synchronousSink
                        -> synchronousSink.next(Util.faker().country().name()))
                .takeUntil(country -> country.equalsIgnoreCase("France"))
                .subscribe(Util.subscriber("Subscriber 2"));
    }
}
