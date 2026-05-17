package fr.stefangeorgesco.reactive_playground.sec08;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SuppressWarnings("DuplicatedCode")
public class Lec03MultipleSubscribers {

    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribers.class);

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
                .map(Lec03MultipleSubscribers::process)
                .subscribe(Util.subscriber("subscriber 1"));

        producer
                .take(100)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber("subscriber 2"));

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
