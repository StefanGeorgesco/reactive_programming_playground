package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec04FluxFromStream {

    public static void main(String[] args) {

        var list = List.of(1, 2, 3, 4, 5, 6);

        // Java Stream can be consumed only once,
        // So we must pass a supplier of stream to Flux.fromStream
        var flux = Flux.fromStream(list::stream);

        flux
                .subscribe(Util.subscriber("Stream subscriber 1"));
        flux
                .subscribe(Util.subscriber("Stream subscriber 2"));

    }
}
