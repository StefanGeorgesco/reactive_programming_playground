package fr.stefangeorgesco.reactive_playground.sec06;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Hot - 1 data producer for all the subscribers.
    share => publish().refCount(1)
    It needs 1 min subscriber to emit data.
    It stops when there are no more subscribers.
    Re-subscription - It starts again where there is a new subscriber.
    To have min 2 subscribers, use publish().refCount(2);
 */

public class Lec02HotPublisher {

    private static final Logger log = LoggerFactory.getLogger(Lec02HotPublisher.class);

    public static void main(String[] args) throws InterruptedException {

        var movieStream = movieStream().share();

        Util.sleepSeconds(2);

        movieStream
                .take(4)
                .subscribe(Util.subscriber("Sam"));

        Util.sleepSeconds(3);

        movieStream
                .take(3)
                .subscribe(Util.subscriber("Mike"));

        Util.sleepSeconds(15);
    }

    // movie theater
    @SuppressWarnings("DuplicatedCode")
    private static Flux<String> movieStream() {
        return Flux.generate(
                        () -> {
                            log.info("received the request");
                            return 1;
                        },
                        (state, synchronousSink) -> {
                            var scene = "movie scene " + state++;
                            log.info("playing {}", scene);
                            synchronousSink.next(scene);
                            return state;
                        }
                )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}
