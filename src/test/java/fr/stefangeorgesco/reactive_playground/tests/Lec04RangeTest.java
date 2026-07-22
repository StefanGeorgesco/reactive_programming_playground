package fr.stefangeorgesco.reactive_playground.tests;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class Lec04RangeTest {

    private Flux<Integer> getItems() {
        return Flux.range(1, 50);
    }

    private Flux<Integer> getRandomItems() {
        return Flux.range(1, 50)
                .map(i -> Util.faker().random().nextInt(1, 100));
    }

    @Test
    void rangeTest1() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3)
                .expectNextCount(47)
                .expectComplete()
                .verify();
    }

    @Test
    void rangeTest2() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3)
                .expectNextCount(22)
                .expectNext(26, 27, 28)
                .expectNextCount(22)
                .expectComplete()
                .verify();
    }

    @Test
    void rangeTest3() {
        StepVerifier.create(getRandomItems())
                .expectNextMatches(i -> i >= 1 && i <= 100)
                .expectNextCount(49)
                .expectComplete()
                .verify();
    }

    @Test
    void rangeTest4() {
        StepVerifier.create(getRandomItems())
                .thenConsumeWhile(i -> i >= 1 && i <= 100)
                .expectComplete()
                .verify();
    }
}
