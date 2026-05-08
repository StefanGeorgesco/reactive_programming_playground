package fr.stefangeorgesco.reactive_playground.sec02;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

/*
    Delaying the publisher creation
 */

public class Lec10MonoDefer {

    private static final Logger logger = LoggerFactory.getLogger(Lec10MonoDefer.class);

    public static void main(String[] args) {
        // Publisher creation is deferred until the subscriber subscribes
        Mono.defer(Lec10MonoDefer::sumPublisher).subscribe(Util.subscriber());
    }

    private static Mono<Integer> sumPublisher() {
        logger.info("creating publisher...");
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var list = List.of(1, 2, 3);
        return Mono.fromCallable(() -> sum(list));
    }

    // time-consuming operation
    private static int sum(List<Integer> list) {
        logger.info("summing {}...", list);
        try {
            Util.sleepSeconds(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list.stream().mapToInt(i -> i).sum();
    }
}
