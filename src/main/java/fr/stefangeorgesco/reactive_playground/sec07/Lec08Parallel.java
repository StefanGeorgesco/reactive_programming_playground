package fr.stefangeorgesco.reactive_playground.sec07;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Often you really do not need this!
    - prefer non-blocking IO for network calls
 */

public class Lec08Parallel {

    private static final Logger log = LoggerFactory.getLogger(Lec08Parallel.class);

    public static void main(String[] args) throws InterruptedException {

        Flux.range(1, 10)
                .parallel(3)
                .runOn(Schedulers.parallel())
                .map(Lec08Parallel::process)
                .sequential()
                .map(i -> i + "a")
                .subscribe(Util.subscriber("Subscriber"));

        Util.sleepSeconds(5);
    }

    private static int process(int i) {
        log.info("Processing {}", i);
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i * 2;
    }
}
