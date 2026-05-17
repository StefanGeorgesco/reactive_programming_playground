package fr.stefangeorgesco.reactive_playground.sec08;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Reactor automatically handles the backpressure.
    We can also adjust the limit
 */

@SuppressWarnings("DuplicatedCode")
public class Lec02LimitRate {

    private static final Logger log = LoggerFactory.getLogger(Lec02LimitRate.class);

    public static void main(String[] args) throws InterruptedException {

        var producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("generating {}", state);
                            sink.next(state);
                            return state + 1;
                        })
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .limitRate(5)
                .publishOn(Schedulers.boundedElastic())
                .map(Lec02LimitRate::process)
                .subscribe(Util.subscriber("subscriber"));

        Util.sleepSeconds(30);
    }

    private static int process(int i) {
        log.info("processing {}", i);
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
