package fr.stefangeorgesco.reactive_playground.sec06;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    - publish().autoConnect(0) will provide new values to the subscribers.
    - replay allows us to cache
 */

public class Lec04HotPublisherCache {

    private static final Logger log = LoggerFactory.getLogger(Lec04HotPublisherCache.class);

    public static void main(String[] args) throws InterruptedException {

        var stockStream = stockStream().replay(1).autoConnect(0);

        Util.sleepSeconds(5);

        log.info("Sam joining");
        stockStream
                .subscribe(Util.subscriber("Sam"));

        Util.sleepSeconds(4);

        log.info("Mike joining");
        stockStream
                .subscribe(Util.subscriber("Mike"));

        Util.sleepSeconds(15);
    }

    // stock stream
    private static Flux<Integer> stockStream() {
        return Flux.generate(sink -> sink.next(Util.faker().random().nextInt(10, 100)))
                .delayElements(Duration.ofSeconds(3))
                .cast(Integer.class)
                .doOnNext(price -> log.info("emitting price: {}", price));
    }
}
