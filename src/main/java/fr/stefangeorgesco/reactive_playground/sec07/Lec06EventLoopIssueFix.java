package fr.stefangeorgesco.reactive_playground.sec07;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec07.client.ExternalServiceClient;

/*
    Ensure that the external service is up and running!
 */

public class Lec06EventLoopIssueFix {

    public static void main(String[] args) throws InterruptedException {

        var client = new ExternalServiceClient();

        for (int i = 1; i <= 5; i++) {
            // See comment in ExternalServiceClient.getProductName method
            client.getProductName(i)
                    .map(Lec06EventLoopIssueFix::process)
                    .subscribe(Util.subscriber(String.format("Subscriber %d", i)));
        }

        Util.sleepSeconds(20);
    }

    private static String process(String input) {
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return input + "-processed";
    }
}
