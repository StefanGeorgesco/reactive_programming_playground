package fr.stefangeorgesco.reactive_playground.sec08;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
    Reactor automatically handles the backpressure,
    through the reactor.bufferSize.small system property,
    with a default queue size of 256
    and a minimum of 16.

    See reactor.util.concurrent.Queues.SMALL_BUFFER_SIZE.
 */

@SuppressWarnings("DuplicatedCode")
public class Lec01BackPressureHandling {

    private static final Logger log = LoggerFactory.getLogger(Lec01BackPressureHandling.class);

    public static void main(String[] args) throws InterruptedException {

        // You generally don't need to do this. Here for demonstration purposes.
        // See Lec02LimitRate for a better practice (rate limiting).
        System.setProperty("reactor.bufferSize.small", "16");

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
                .publishOn(Schedulers.boundedElastic())
                .map(Lec01BackPressureHandling::process)
                .subscribe(Util.subscriber("Subscriber"));

        Util.sleepSeconds(30);
    }

    private static int process(int i) {
        try {
            Util.sleepSeconds(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
