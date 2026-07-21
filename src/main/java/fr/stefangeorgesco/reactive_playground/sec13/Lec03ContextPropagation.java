package fr.stefangeorgesco.reactive_playground.sec13;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

import java.time.Duration;

@SuppressWarnings({"DuplicatedCode", "unused"})
public class Lec03ContextPropagation {

    private static final Logger log = LoggerFactory.getLogger(Lec03ContextPropagation.class);

    public static void main(String[] args) {
        getWelcomeMessage()
                .concatWith(Flux.merge(producer1(), producer2()))
                .contextWrite(Context.of("username", "admin", "password", "password"))
                .subscribe(Util.subscriber("subscriber"));

        Util.sleep(Duration.ofSeconds(2));
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("username") && ctx.hasKey("password") &&
                    "admin".equals(ctx.<String>get("username")) && "password".equals(ctx.<String>get("password"))) {
                return Mono.just("%s logged successfully".formatted(ctx.<String>get("username")));
            }
            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }

    private static Mono<String> producer1() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer1: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> producer2() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer2: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.parallel());
    }
}
