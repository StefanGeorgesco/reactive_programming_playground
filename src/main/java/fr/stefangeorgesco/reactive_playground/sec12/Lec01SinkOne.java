package fr.stefangeorgesco.reactive_playground.sec12;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@SuppressWarnings({"CommentedOutCode", "unused", "LoggingSimilarMessage"})
public class Lec01SinkOne {

    private static final Logger log = LoggerFactory.getLogger(Lec01SinkOne.class);

    public static void main(String[] args) {
        demo3();
    }

    // exploring sink methods to emit item / empty / error
    private static void demo1() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("sink subscriber"));
        sink.tryEmitValue("Hello");
//        sink.tryEmitEmpty();
//        sink.tryEmitError(new RuntimeException("boom"));
    }

    // we can have multiple subscribers
    private static void demo2() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        sink.tryEmitValue("Hello");
        // the sink is a hot publisher, so it can be subscribed to at any moment
        mono.subscribe(Util.subscriber("sink subscriber 1"));
        mono.subscribe(Util.subscriber("sink subscriber 2"));
    }

    private static void demo3() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("sink subscriber"));

        sink.emitValue(
                "Hi",
                // the failureHandler callback is not invoked because the sink one can emit the first value
                (signalType, emitResult) -> {
                    log.info("Hi");
                    log.info("signalType: {}, emitResult: {}", signalType, emitResult);
                    return false;
                }
        );

        sink.emitValue(
                "Hello",
                // the failureHandler callback is invoked because the sink one can't emit the second value
                (signalType, emitResult) -> {
                    log.info("Hello");
                    log.info("signalType: {}, emitResult: {}", signalType, emitResult);
                    // Do not retry
                    return false;
                }
        );
    }
}
