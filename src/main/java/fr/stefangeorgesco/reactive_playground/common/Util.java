package fr.stefangeorgesco.reactive_playground.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

public class Util {

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

    public static void main(String[] args) {
        var mono = Mono.just(1);
        mono.subscribe(subscriber());
        mono.subscribe(subscriber("subscriber 1"));
        mono.subscribe(subscriber("subscriber 2"));
    }
}
