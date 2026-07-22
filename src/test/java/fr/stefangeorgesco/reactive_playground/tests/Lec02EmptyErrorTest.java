package fr.stefangeorgesco.reactive_playground.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Lec02EmptyErrorTest {

    @SuppressWarnings("SameParameterValue")
    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new IllegalArgumentException("invalid user id"));
        };
    }

    @Test
    void testGetUsernameReturnsValue() {
        StepVerifier.create(getUsername(1)
                        .doFirst(() -> System.out.println("Fetching username for userId 1")))
                .expectNext("sam")
                .verifyComplete();
    }

    @Test
    void testGetUsernameReturnsEmpty() {
        StepVerifier.create(getUsername(2)
                        .doFirst(() -> System.out.println("Fetching username for userId 2")))
                .verifyComplete();
    }

    @Test
    void testGetUsernameReturnsError() {
        StepVerifier.create(getUsername(3)
                        .doFirst(() -> System.out.println("Fetching username for userId 3")))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void testGetUsernameReturnsErrorMessage() {
        StepVerifier.create(getUsername(3)
                        .doFirst(() -> System.out.println("Fetching username for userId 3")))
                .expectErrorMessage("invalid user id")
                .verify();
    }

    @Test
    void testGetUsernameReturnsErrorAndMessage() {
        StepVerifier.create(getUsername(3)
                        .doFirst(() -> System.out.println("Fetching username for userId 3")))
                .consumeErrorWith(
                        ex -> {
                            assertEquals(IllegalArgumentException.class, ex.getClass());
                            assertEquals("invalid user id", ex.getMessage());
                        }
                )
                .verify();
    }
}
