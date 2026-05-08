package fr.stefangeorgesco.reactive_playground.sec02;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/*
    Creating publisher is a lightweight operation.
    Executing time-consuming business logic can be delayed
 */

public class Lec09PublisherCreateVsExecute {

    private static final Logger logger = LoggerFactory.getLogger(Lec09PublisherCreateVsExecute.class);

    public static void main(String[] args) {
        getFirstName().subscribe(Util.subscriber());
    }

    private static Mono<String> getFirstName() {
        // Executed immediately
        logger.info("creating publisher...");
        // Execution of callable is deferred until the subscriber subscribes
        return Mono.fromCallable(() -> {
            logger.info("generating name...");
            Util.sleepSeconds(3);
            return Util.faker().name().firstName();
        });
    }

}
