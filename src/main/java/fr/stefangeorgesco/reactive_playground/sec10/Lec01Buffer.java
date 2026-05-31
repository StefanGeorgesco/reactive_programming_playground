package fr.stefangeorgesco.reactive_playground.sec10;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Collect items based on given interval / size
 */
@SuppressWarnings("unused")
public class Lec01Buffer {

    public static void main(String[] args) {

        demo4();

        Util.sleep(Duration.ofSeconds(15));
    }

    private static void demo1() {
        eventStream()
                .buffer()// int max value or completion signal
                .subscribe(Util.subscriber("default buffer subscriber"));
    }

    private static void demo2() {
        eventStream()
                .buffer(3)// every 3 events
                .subscribe(Util.subscriber("3-item buffer subscriber"));
    }

    private static void demo3() {
        eventStream()
                .buffer(Duration.ofMillis(500))// every 500 ms
                .subscribe(Util.subscriber("500ms-buffer subscriber"));
    }

    private static void demo4() {
        eventStream()
                .bufferTimeout(3, Duration.ofSeconds(1))// every 3 events
                .subscribe(Util.subscriber("3-item buffer subscriber"));
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .take(10)
                .concatWith(Flux.never())
                .map(i -> "event-" + (i + 1));
    }
}
