package fr.stefangeorgesco.reactive_playground.sec12;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@SuppressWarnings("DuplicatedCode")
public class Lec06SinkMulticastDirectAllOrNothing {

    private static final Logger log = LoggerFactory.getLogger(Lec06SinkMulticastDirectAllOrNothing.class);

    public static void main(String[] args) {
        demo1();
    }

    /*
        directAllOrNothing - all or nothing - either deliver to all the subscribers or none!
    */
    private static void demo1() {

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().directAllOrNothing();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("subscriber 1"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("subscriber 2"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleep(Duration.ofSeconds(10));
    }
}
