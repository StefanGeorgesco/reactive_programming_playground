package fr.stefangeorgesco.reactive_playground.sec07;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    We can have multiple subscribeOn.
    The closest to the source will take the precedence!
 */

public class Lec03MultipleSubscribeOn {

    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribeOn.class);

    public static void main(String[] args) throws InterruptedException {

        var flux = createFlux()
                .doOnNext(i -> log.info("value: {}", i))
                .doFirst(() -> log.info("doFirst1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("doFirst2"));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("Subscriber"));

        Thread.ofPlatform().start(runnable);

        Util.sleepSeconds(2);
    }

    private static Flux<Integer> createFlux() {
        return Flux.create(fluxSink -> {
                    for (int i = 1; i < 10; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .subscribeOn(Schedulers.newParallel("sgo"))
                .cast(Integer.class);
    }
}
