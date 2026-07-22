package fr.stefangeorgesco.reactive_playground.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class Lec03FluxTest {

    private Flux<Integer> getItems() {
        return Flux.just(1, 2, 3)
                .log();
    }

    @Test
    void fluxTest1() {
        StepVerifier.create(getItems(), 2)
                .expectNext(1)
                .expectNext(2)
                .thenCancel()
                .verify();
    }

    @Test
    void fluxTest2() {
        StepVerifier.create(getItems())
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectComplete()
                .verify();
    }

    @Test
    void fluxTest3() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3)
                .expectComplete()
                .verify();
    }
}
