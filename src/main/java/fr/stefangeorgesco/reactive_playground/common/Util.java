package fr.stefangeorgesco.reactive_playground.common;

import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

public class Util {

    public static <T> Subscriber<T> subscriber() {
        return subscriber("unnamed");
    }

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static void main(String[] args) {
        var mono = Mono.just(1);
        mono.subscribe(subscriber());
        mono.subscribe(subscriber("subscriber 1"));
        mono.subscribe(subscriber("subscriber 2"));
    }
}
