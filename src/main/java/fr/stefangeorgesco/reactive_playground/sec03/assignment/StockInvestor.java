package fr.stefangeorgesco.reactive_playground.sec03.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ReactiveStreamsSubscriberImplementation")
public class StockInvestor implements Subscriber<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(StockInvestor.class);
    private Subscription subscription;
    private int balance = 1000;
    private int stockCount = 0;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Integer.MAX_VALUE);
    }

    @Override
    public void onNext(Integer price) {
        logger.info("Received stock price: {}", price);
        if (price < 90) {
            if (balance >= price) {
                balance -= price;
                stockCount++;
                logger.info("Bought a stock at ${}, stock count: {}, stock value: ${}. New balance: ${}",
                        price, stockCount, price * stockCount, balance);
            } else {
                logger.info("Insufficient balance to buy the stock.");
            }
        } else if (price > 110 && stockCount > 0) {
            balance += price * stockCount;
            logger.info("Stock price: ${}. Sold all stocks ({}), for ${}. New balance: ${}, profit: ${}",
                    price, stockCount, price * stockCount, balance, balance - 1000);
            stockCount = 0;
            subscription.cancel();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("error", throwable);
    }

    @Override
    public void onComplete() {
        logger.info("Subscription to stock price stream completed");
    }
}
