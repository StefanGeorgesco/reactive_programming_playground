package fr.stefangeorgesco.reactive_playground.sec10;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec05GroupedFlux {

    private static final Logger log = LoggerFactory.getLogger(Lec05GroupedFlux.class);

    public static void main(String[] args) {

        Flux.range(0, 30)
                .delayElements(Duration.ofSeconds(1))
                .groupBy(i -> i % 5)
                .flatMap(Lec05GroupedFlux::processEvents)
                .subscribe();

        Util.sleep(Duration.ofSeconds(30));
    }

    private static Mono<Void> processEvents(GroupedFlux<Integer, Integer> groupedFlux) {
        log.info("received flux for key {}", groupedFlux.key());
        return groupedFlux.doOnNext(item -> log.info("key : {}, item: {}", groupedFlux.key(), item))
                .then();
    }
}
