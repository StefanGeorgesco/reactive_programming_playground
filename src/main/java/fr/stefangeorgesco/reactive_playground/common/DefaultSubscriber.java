package fr.stefangeorgesco.reactive_playground.common;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ReactiveStreamsSubscriberImplementation")
public class DefaultSubscriber<T> implements Subscriber<T> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSubscriber.class);
    private final String name;

    public DefaultSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        logger.info("{} received {}", this.name, item);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("{} received error", this.name, throwable);
    }

    @Override
    public void onComplete() {
        logger.info("{} received completed!", this.name);
    }
}
