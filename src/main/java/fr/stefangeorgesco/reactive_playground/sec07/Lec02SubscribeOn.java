package fr.stefangeorgesco.reactive_playground.sec07;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOn {

    private static final Logger log = LoggerFactory.getLogger(Lec02SubscribeOn.class);

    public static void main(String[] args) throws InterruptedException {

        var flux = Flux.create(fluxSink -> {
                    for (int i = 1; i < 10; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .doOnNext(i -> log.info("value: {}", i))
                .doFirst(() -> log.info("doFirst1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("doFirst2"));

        Runnable runnable1 = () -> flux.subscribe(Util.subscriber("Subscriber 1"));
        Runnable runnable2 = () -> flux.subscribe(Util.subscriber("Subscriber 2"));

        Thread.ofPlatform().start(runnable1);
        Thread.ofPlatform().start(runnable2);

        Util.sleepSeconds(2);
    }
}
