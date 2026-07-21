package fr.stefangeorgesco.reactive_playground.sec13;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec13.client.ExternalServiceClient;
import reactor.util.context.Context;

import java.time.Duration;

/*
    Ensure that the external service is up and running!
 */

public class Lec04ContextRateLimiterDemo {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 1; i <= 20; i++) {
            client.getBook()
                    .contextWrite(Context.of("user", "mike")) // prime user
                    .subscribe(Util.subscriber("book subscriber " + i));
            Util.sleep(Duration.ofSeconds(1));
        }

        Util.sleep(Duration.ofSeconds(1));
    }
}
