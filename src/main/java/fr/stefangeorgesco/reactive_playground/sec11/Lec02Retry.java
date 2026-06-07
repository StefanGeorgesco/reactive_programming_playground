package fr.stefangeorgesco.reactive_playground.sec11;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class Lec02Retry {

    private static final Logger log = LoggerFactory.getLogger(Lec02Retry.class);

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        getCountryName()
                .retry(3)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(3, Duration.ofSeconds(1))
                                .filter(RuntimeException.class::isInstance)
                                .doBeforeRetry(rs -> log.info("retrying ({})...", rs.totalRetries() + 1))
                                // throw the original exception when the retry count is exhausted
                                .onRetryExhaustedThrow((spec, signal) -> signal.failure())
                )
                .subscribe(Util.subscriber());

        Util.sleep(Duration.ofSeconds(5));
    }

    private static Mono<String> getCountryName() {
        var atomicInteger = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {
                    if (atomicInteger.getAndIncrement() < 3) {
                        throw new RuntimeException("boom");
                    } else {
                        return Util.faker().country().name();
                    }
                })
                .doOnSubscribe(s -> log.info("subscribing"))
                .doOnError(e -> log.info("error: {}", e.getMessage()))
                .doOnNext(s -> log.info("value: {}", s));
    }
}
