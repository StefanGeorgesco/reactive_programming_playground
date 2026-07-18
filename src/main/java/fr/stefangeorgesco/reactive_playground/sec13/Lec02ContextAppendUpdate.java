package fr.stefangeorgesco.reactive_playground.sec13;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

/*
    Context is an immutable map. We can append additional info!
 */

@SuppressWarnings({"DuplicatedCode", "unused"})
public class Lec02ContextAppendUpdate {

    private static final Logger log = LoggerFactory.getLogger(Lec02ContextAppendUpdate.class);

    public static void main(String[] args) {
        update();
    }

    private static void append() {
        getWelcomeMessage()
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f").putAllMap(Map.of("g", "h")))
                .contextWrite(Context.of("username", "admin", "password", "password"))
                .subscribe(Util.subscriber("subscriber"));
    }

    private static void update() {
        getWelcomeMessage()
                // works from bottom to top
                .contextWrite(ctx -> ctx.delete("c"))
                .contextWrite(ctx -> ctx.put("e", ctx.<String>get("e").toUpperCase()))
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f").putAllMap(Map.of("g", "h")))
                .contextWrite(Context.of("username", "admin", "password", "password"))
                .subscribe(Util.subscriber("subscriber"));
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            log.info("{}", ctx);
            if (ctx.hasKey("username") && ctx.hasKey("password") &&
                    "admin".equals(ctx.<String>get("username")) && "password".equals(ctx.<String>get("password"))) {
                return Mono.just("%s logged successfully".formatted(ctx.<String>get("username")));
            }
            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }
}
