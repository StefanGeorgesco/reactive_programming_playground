package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.sec01.subscriber.SubscriberImpl;
import fr.stefangeorgesco.reactive_playground.sec03.helper.NameGenerator;

public class Lec07FluxVsList {

    public static void main(String[] args) {

        System.out.println("Flux:");
        var subscriber = new SubscriberImpl();
        var flux = NameGenerator.getNamesFlux(10);
        // Items will be received as they are generated (every 1 second)
        flux.subscribe(subscriber);
        // Can request the desired number of items
        subscriber.getSubscription().request(3);
        // Can cancel the subscription to stop the name generation early
        subscriber.getSubscription().cancel();

        // Items will be received when the stream is completed (after 10 seconds)
        // Cannot request a given number of items
        // Cannot cancel the name generation early
        System.out.println("\nList:");
        var list = NameGenerator.getNamesList(10);
        System.out.println(list);
    }
}
