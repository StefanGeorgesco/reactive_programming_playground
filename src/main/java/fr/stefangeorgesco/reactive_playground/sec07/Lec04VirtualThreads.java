package fr.stefangeorgesco.reactive_playground.sec07;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    reactor supports virtual threads
    System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
 */

@SuppressWarnings("DuplicatedCode")
public class Lec04VirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(Lec04VirtualThreads.class);

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var flux = Flux.create(fluxSink -> {
                    for (int i = 1; i < 10; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .doOnNext(i -> log.info("value: {}", i))
                .doFirst(() -> log.info("doFirst1-{}", Thread.currentThread().isVirtual()))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("doFirst2-{}", Thread.currentThread().isVirtual()));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("Subscriber"));

        Thread.ofPlatform().start(runnable);

        Util.sleepSeconds(2);
    }
}
