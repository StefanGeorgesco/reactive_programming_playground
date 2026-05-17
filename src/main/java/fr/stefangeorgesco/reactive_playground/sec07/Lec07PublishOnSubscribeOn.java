package fr.stefangeorgesco.reactive_playground.sec07;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    publishOn for downstream
    subscribeOn for upstream
 */

@SuppressWarnings("DuplicatedCode")
public class Lec07PublishOnSubscribeOn {

    private static final Logger log = LoggerFactory.getLogger(Lec07PublishOnSubscribeOn.class);

    public static void main(String[] args) throws InterruptedException {

        var flux = Flux.create(fluxSink -> {
                    for (int i = 1; i < 10; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .publishOn(Schedulers.parallel())
                .doOnNext(i -> log.info("value: {}", i))
                .doFirst(() -> log.info("doFirst1"))
                .subscribeOn(Schedulers.parallel())
                .doFirst(() -> log.info("doFirst2"));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("Subscriber"));

        Thread.ofPlatform().start(runnable);

        Util.sleepSeconds(2);
    }
}
