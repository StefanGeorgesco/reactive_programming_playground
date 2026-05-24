package fr.stefangeorgesco.reactive_playground.sec08;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@SuppressWarnings("DuplicatedCode")
public class Lec04FluxCreate {

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreate.class);

    public static void main(String[] args) throws InterruptedException {

        var producer = Flux.create(fluxSink -> {
                    for (int i = 1; i <= 500 && !fluxSink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                        Util.sleep(Duration.ofMillis(50));
                    }
                    fluxSink.complete();
                })
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic())
                .map(Lec04FluxCreate::process)
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static int process(int i) {
        log.info("processing {}", i);
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
