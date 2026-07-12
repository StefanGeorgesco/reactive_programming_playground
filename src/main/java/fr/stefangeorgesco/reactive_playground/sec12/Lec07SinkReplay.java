package fr.stefangeorgesco.reactive_playground.sec12;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@SuppressWarnings("DuplicatedCode")
public class Lec07SinkReplay {

    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
        // handle through which we would push items
        var sink = Sinks.many().replay().all();
//        var sink = Sinks.many().replay().limit(2);

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
}
