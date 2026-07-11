package fr.stefangeorgesco.reactive_playground.sec12;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@SuppressWarnings({"unused", "DuplicatedCode", "LoggingSimilarMessage"})
public class Lec05SinkMulticastDirectBestEffort {

    private static final Logger log = LoggerFactory.getLogger(Lec05SinkMulticastDirectBestEffort.class);

    public static void main(String[] args) {
        demo1();
    }

    /*
        When we have multiple subscribers, if one subscriber is slow,
        we might not be able to safely deliver messages to all the subscribers /
        other fast subscribers might not get the messages.
     */
    private static void demo1() {

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("subscriber 1"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("subscriber 2"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleep(Duration.ofSeconds(21));
    }

    /*
        directBestEffort - focus on the fast subscriber and ignore the slow subscriber
     */
    private static void demo2() {

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().directBestEffort();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("subscriber 1"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("subscriber 2"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleep(Duration.ofSeconds(21));
    }
}
