package fr.stefangeorgesco.reactive_playground.sec12;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@SuppressWarnings("unused")
public class Lec04SinkMulticast {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("subscriber 1"));
        flux.subscribe(Util.subscriber("subscriber 2"));

        sink.tryEmitNext("Hello!");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");

        Util.sleep(Duration.ofSeconds(2));

        flux.subscribe(Util.subscriber("subscriber 3"));

        sink.tryEmitNext("new message");
    }

    // Warmup
    private static void demo2() {
        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        sink.tryEmitNext("Hello!");
        sink.tryEmitNext("How are you");
        sink.tryEmitNext("?");

        Util.sleep(Duration.ofSeconds(2));

        flux.subscribe(Util.subscriber("subscriber 1"));
        flux.subscribe(Util.subscriber("subscriber 2"));
        flux.subscribe(Util.subscriber("subscriber 3"));

        sink.tryEmitNext("new message");
    }
}
