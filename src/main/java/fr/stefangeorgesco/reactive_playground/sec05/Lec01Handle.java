package fr.stefangeorgesco.reactive_playground.sec05;

/*
    Handle behaves like filter + map:
    1 => -2
    4 => do not send item
    7 => error
    everything else => send as it is
*/

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .filter(i -> i != 7)
                .handle((i, sink) -> {
                    switch (i) {
                        case 1 -> sink.next(-2);
                        case 4 -> {
                            // do not send item
                        }
                        case 7 -> sink.error(new RuntimeException("Error"));
                        default -> sink.next(i);
                    }
                })
                .cast(Integer.class)
                .subscribe(Util.subscriber("Subscriber"));
    }
}
