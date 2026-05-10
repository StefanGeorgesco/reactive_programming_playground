package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxJust {

    public static void main(String[] args) {
        Flux.just(1, 2, 3, "sam").subscribe(Util.subscriber());
    }
}
