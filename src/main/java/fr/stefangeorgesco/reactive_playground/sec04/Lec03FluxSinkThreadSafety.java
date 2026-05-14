package fr.stefangeorgesco.reactive_playground.sec04;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

/*
 * FluxSink is thread-safe
 */

@SuppressWarnings("LoggingSimilarMessage")
public class Lec03FluxSinkThreadSafety {

    private static final Logger logger = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);

    public static void main(String[] args) throws InterruptedException {
        demo1();
        demo2();
    }

    // Not thread-safe
    private static void demo1() throws InterruptedException {
        var list = new ArrayList<String>();

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(Util.faker().name().firstName());
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3);

        logger.info("list size: {}", list.size());
    }

    // Thread-safe
    private static void demo2() throws InterruptedException {
        var list = new ArrayList<String>();
        var generator = new NameGenerator();
        var flux = Flux.create(generator);
        flux.subscribe(list::add);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                generator.generate();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3);

        logger.info("list size: {}", list.size());
    }
}
