package fr.stefangeorgesco.reactive_playground.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

class Lec08ContextTest {

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("username") && ctx.hasKey("password") &&
                    "admin".equals(ctx.<String>get("username")) && "password".equals(ctx.<String>get("password"))) {
                return Mono.just("%s logged successfully".formatted(ctx.<String>get("username")));
            }
            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }

    @Test
    void testGetWelcomeMessage() {
        var options = StepVerifierOptions.create()
                .withInitialContext(Context.of("username", "admin", "password", "password"));

        StepVerifier.create(getWelcomeMessage(), options)
                .expectNext("admin logged successfully")
                .verifyComplete();
    }

    @Test
    void testGetWelcomeMessageUnauthenticated() {
        var options = StepVerifierOptions.create()
                .withInitialContext(Context.of("username", "admin", "password", "wrongpassword"));

        StepVerifier.create(getWelcomeMessage(), options)
                .expectErrorMessage("unauthenticated")
                .verify();
    }
}
