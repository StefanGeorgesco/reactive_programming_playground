package fr.stefangeorgesco.reactive_playground.sec02;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Lec03MonoSubscribe.class);

    public static void main(String[] args) {
        var mono = Mono.just(1);

        mono.subscribe(
                i -> logger.info("received {}", i),
                err -> logger.error("error", err),
                () -> logger.info("completed"));
    }
}
