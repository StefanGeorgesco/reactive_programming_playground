package fr.stefangeorgesco.reactive_playground.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.util.function.UnaryOperator;

class Lec09PublisherTest {

    private UnaryOperator<Flux<String>> processor() {
        return flux -> flux
                .filter(s -> s.length() > 1)
                .map(String::toUpperCase)
                .map(s -> s + ":" + s.length());
    }

    @Test
    void publisherTest1() {
        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();

        StepVerifier.create(flux.transform(processor()))
                .then(() -> publisher.emit("a", "bb", "ccc"))
                .expectNext("BB:2", "CCC:3")
                .verifyComplete();
    }
}
