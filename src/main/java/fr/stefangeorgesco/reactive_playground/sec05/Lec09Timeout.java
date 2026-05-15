package fr.stefangeorgesco.reactive_playground.sec05;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/*
    timeout:
        - will produce a timeout error
        - we can handle as part of onError methods
        - there is also an overloaded method to accept a publisher
        - we can have multiple timeouts: the closest one to the subscriber will take effect for the subscriber
 */

public class Lec09Timeout {

    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout.class);

    public static void main(String[] args) throws InterruptedException {

        var mono = getProductName()
                // would produce a timeout error after 1 second
                .timeout(Duration.ofSeconds(1));

        mono
                // no error: fallback provided after 500 ms
                .timeout(Duration.ofMillis(500), fallback())
                .subscribe(Util.subscriber("Product name subscriber"));

        Util.sleepSeconds(2);
    }

    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "service - " + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(1900));
    }

    private static Mono<String> fallback() {
        return Mono.fromSupplier(() -> "fallback - " + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(300))
                .doFirst(() -> log.info("callback do first"));
    }
}
