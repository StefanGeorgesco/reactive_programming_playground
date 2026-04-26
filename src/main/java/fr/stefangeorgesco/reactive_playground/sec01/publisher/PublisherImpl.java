package fr.stefangeorgesco.reactive_playground.sec01.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

@SuppressWarnings("ReactiveStreamsPublisherImplementation")
public class PublisherImpl implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        var subscription = new SubscriptionImpl(subscriber);
        subscriber.onSubscribe(subscription);
    }
}
