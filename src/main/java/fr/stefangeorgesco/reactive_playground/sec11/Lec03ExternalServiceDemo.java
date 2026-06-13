package fr.stefangeorgesco.reactive_playground.sec11;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec11.client.ExternalServiceClient;
import fr.stefangeorgesco.reactive_playground.sec11.client.ServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.retry.Retry;

import java.time.Duration;

@SuppressWarnings("unused")
public class Lec03ExternalServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec03ExternalServiceDemo.class);

    private static final String SUBSCRIBER_NAME = "country subscriber";

    public static void main(String[] args) {
        retry();
    }

    private static void repeat() {
        var client = new ExternalServiceClient();
        client.getCountry()
                .repeat()
                .takeUntil(country -> country.equalsIgnoreCase("France"))
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
        Util.sleep(Duration.ofSeconds(60));
    }

    private static void retry() {
        var client = new ExternalServiceClient();
        client.getProductName(2)
                .retryWhen(retryOnServerError())
                .subscribe(Util.subscriber(SUBSCRIBER_NAME));
        Util.sleep(Duration.ofSeconds(10));
    }

    private static Retry retryOnServerError() {
        return Retry.fixedDelay(20, Duration.ofSeconds(1))
                .filter(ServerError.class::isInstance)
                .doBeforeRetry(rs -> log.info("retrying ({})...", rs.totalRetries() + 1));
    }
 }
