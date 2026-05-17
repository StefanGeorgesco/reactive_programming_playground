package fr.stefangeorgesco.reactive_playground.sec07;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    By default, the current thread is doing all the work
 */

public class Lec01DefaultBehaviorDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec01DefaultBehaviorDemo.class);

    public static void main(String[] args) {

        var flux = Flux.create(fluxSink -> {
                    for (int i = 1; i < 10; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .doOnNext(i -> log.info("value: {}", i));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("Subscriber"));

        Thread.ofPlatform().start(runnable);
    }
}
