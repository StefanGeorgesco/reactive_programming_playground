package fr.stefangeorgesco.reactive_playground.sec06;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    almost same as publish().refCount(1).
    - does NOT stop when subscribers cancel. So it will start producing even for 0 subscribers once it started.
    - make it real hot - publish().autoConnect(0). So it will start right away even without any subscriber.
 */

public class Lec03HotPublisherAutoConnect {

    private static final Logger log = LoggerFactory.getLogger(Lec03HotPublisherAutoConnect.class);

    public static void main(String[] args) throws InterruptedException {

        var movieStream = movieStream().publish().autoConnect();

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
