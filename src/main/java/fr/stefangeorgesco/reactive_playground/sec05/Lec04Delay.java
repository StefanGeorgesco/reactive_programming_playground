package fr.stefangeorgesco.reactive_playground.sec05;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {

    public static void main(String[] args) throws InterruptedException {

        Flux.range(1, 10)
                .log("range > delay")
                // requests elements one by one every second
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber("Delay subscriber"));

        Util.sleepSeconds(11);
    }
}
