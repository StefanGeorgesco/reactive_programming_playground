package fr.stefangeorgesco.reactive_playground.sec12;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Sinks;

public class Lec02SinkUnicast {

    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
        // handle through which we would push items
        // onBackPressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        sink.tryEmitNext("Hello!");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");

        // Only one subscriber allowed
        flux.subscribe(Util.subscriber("subscriber"));
    }
}
