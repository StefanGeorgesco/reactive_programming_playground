package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec10FluxEmptyAndError {

    public static void main(String[] args) {

        Flux.empty()
                .subscribe(Util.subscriber("Empty subscriber"));

        Flux.error(new RuntimeException("Error"))
                .subscribe(Util.subscriber("Error subscriber"));
    }
}
