package fr.stefangeorgesco.reactive_playground.sec04;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {

    public static void main(String[] args) {

        Flux.create(fluxSink -> {
            String country;
            do {
                country = Util.faker().country().name();
                fluxSink.next(country);
            } while (!country.equalsIgnoreCase("France"));
            fluxSink.complete();
        }).subscribe(Util.subscriber());
    }
}
