package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {

    public static void main(String[] args) throws InterruptedException {

        Flux.interval(Duration.ofMillis(500))
                .subscribe(Util.subscriber("Interval subscriber"));

        // Flux.interval is non-blocking
        Util.sleepSeconds(5);
    }
}
