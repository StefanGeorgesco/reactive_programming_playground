package fr.stefangeorgesco.reactive_playground.sec01.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionImpl.class);
    private static final int MAX_ITEMS = 10;
    private final Faker faker;
    private final Subscriber<? super String> subscriber;
    private boolean isCancelled = false;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long requested) {
        if (isCancelled) {
            return;
        }
        logger.info("subscriber has requested {} items", requested);
        if (requested > MAX_ITEMS) {
            subscriber.onError(new RuntimeException("requested items is greater than max items"));
            this.isCancelled = true;
            return;
        }
        for (int i = 0; i < requested && count < MAX_ITEMS; i++, count++) {
            subscriber.onNext(faker.internet().emailAddress());
        }
        if (count == MAX_ITEMS) {
            logger.info("no more items to produce");
            this.subscriber.onComplete();
            this.isCancelled = true;
        }
    }

    @Override
    public void cancel() {
        logger.info("subscriber has canceled the subscription");
        isCancelled = true;
    }
}
