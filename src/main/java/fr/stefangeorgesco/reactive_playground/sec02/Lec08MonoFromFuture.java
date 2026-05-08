package fr.stefangeorgesco.reactive_playground.sec02;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Lec08MonoFromFuture {

    private static final Logger logger = LoggerFactory.getLogger(Lec08MonoFromFuture.class);

    public static void main(String[] args) throws InterruptedException {
        // Execution of supplier is deferred until the subscriber subscribes
        // Because we use a supplier of CompletableFuture
        Mono.fromFuture(Lec08MonoFromFuture::getFullName).subscribe(Util.subscriber());
        Util.sleepSeconds(1);
    }

    private static CompletableFuture<String> getFullName() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("generating name");
            return Util.faker().name().fullName();
        });
    }

}
