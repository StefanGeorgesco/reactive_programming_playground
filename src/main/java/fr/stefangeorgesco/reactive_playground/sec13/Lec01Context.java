package fr.stefangeorgesco.reactive_playground.sec13;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@SuppressWarnings("DuplicatedCode")
public class Lec01Context {

    public static void main(String[] args) {
        getWelcomeMessage()
                .contextWrite(Context.of("username", "admin", "password", "password"))
                .subscribe(Util.subscriber("subscriber"));
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
}
