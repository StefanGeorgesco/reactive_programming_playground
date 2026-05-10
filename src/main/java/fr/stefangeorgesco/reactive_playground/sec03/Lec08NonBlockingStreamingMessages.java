package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec03.client.ExternalServiceClient;

public class Lec08NonBlockingStreamingMessages {

    public static void main(String[] args) throws InterruptedException {
        var client = new ExternalServiceClient();

        client.getNames().subscribe(Util.subscriber("Names subscriber 1"));

        client.getNames().subscribe(Util.subscriber("Names subscriber 2"));

        // HttpClient is non-blocking
        Util.sleepSeconds(6);
    }
}
