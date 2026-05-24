package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SuppressWarnings("unused")
public class Lec04ConcatError {

    private static final Logger log = LoggerFactory.getLogger(Lec04ConcatError.class);

    public static void main(String[] args) {

        demo2();

        Util.sleep(Duration.ofSeconds(1));
    }

    private static void demo1() {
        producer1()
                .concatWith(errorProducer())
                .concatWith(producer2())
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        Flux.concatDelayError(producer1(), errorProducer(), producer2())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .doOnSubscribe(s -> log.info("subscribing to producer 1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .doOnSubscribe(s -> log.info("subscribing to producer 2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> errorProducer() {
        return Flux
                .error(new RuntimeException("error from producer 3"))
                .doOnSubscribe(s -> log.info("subscribing to error producer"))
                .cast(Integer.class);
    }
}
