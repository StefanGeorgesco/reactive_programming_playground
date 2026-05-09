package fr.stefangeorgesco.reactive_playground.sec02;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    To demo non-blocking IO
    Ensure that the external service is up and running!
 */

public class Lec11NonBlockingIO {

    private static final Logger logger = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) throws InterruptedException {
        var client = new ExternalServiceClient();

        for (int i = 1; i <= 100; i++) {
            logger.info("Sending request {} to external service", i);
            client.getProductName(i).subscribe(Util.subscriber(String.format("Subscriber %d", i)));
        }

        Util.sleepSeconds(2);
    }
}
