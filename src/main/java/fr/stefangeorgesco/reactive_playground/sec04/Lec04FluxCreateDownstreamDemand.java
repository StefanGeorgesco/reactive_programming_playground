package fr.stefangeorgesco.reactive_playground.sec04;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

/*
* Flux create does not check the downstream demand by default!
 */

@SuppressWarnings({"LoggingSimilarMessage", "DuplicatedCode"})
public class Lec04FluxCreateDownstreamDemand {

    private static final Logger logger = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemand.class);

    public static void main(String[] args) throws InterruptedException {
        produceEarly();
        Util.sleepSeconds(2);
        produceOnDemand();
    }

    private static void produceEarly() throws InterruptedException {
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                var name = Util.faker().name().firstName();
                logger.info("generated: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
    }

    private static  void produceOnDemand() throws InterruptedException {
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            AtomicInteger count = new AtomicInteger();
            fluxSink.onRequest(n -> {
                for (int i = 0; i < n && !fluxSink.isCancelled(); i++) {
                    if (count.getAndIncrement() == 10) {
                        fluxSink.complete();
                        return;
                    }
                    var name = Util.faker().name().firstName();
                    logger.info("generated: {}", name);
                    fluxSink.next(name);
                }
            });

        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
    }
}
