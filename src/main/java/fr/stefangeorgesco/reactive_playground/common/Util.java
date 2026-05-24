package fr.stefangeorgesco.reactive_playground.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.UnaryOperator;

public class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);
    private  static final Faker faker = Faker.instance();

    public static <T> Subscriber<T> subscriber() {
        return subscriber("unnamed");
    }

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static Faker faker() {
        return faker;
    }

    public static void sleepSeconds(int seconds) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(seconds));
    }

    public static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T>UnaryOperator<Flux<T>> fluxLogger(String name) {
        return flux -> flux
                .doOnSubscribe(s -> log.info("subscribing to {}", name))
                .doOnCancel(() -> log.info("cancelling subscription to {}", name))
                .doOnComplete(() -> log.info("completed subscription to {}", name));
    }

    public static void main(String[] args) {
        var mono = Mono.just(1);
        mono.subscribe(subscriber());
        mono.subscribe(subscriber("subscriber 1"));
        mono.subscribe(subscriber("subscriber 2"));
    }
}
