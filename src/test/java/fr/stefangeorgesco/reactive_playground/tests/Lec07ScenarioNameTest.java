package fr.stefangeorgesco.reactive_playground.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

class Lec07ScenarioNameTest {

    private Flux<Integer> getItems() {
        return Flux.range(1, 3);
    }

    @Test
    void scenarioNameTest() {
        // scenario name shown if the test fails
        var options = StepVerifierOptions.create().scenarioName("1 to 3 test");

        StepVerifier.create(getItems(), options)
                .expectNext(1)
                // test step name shown if the test step fails
                .as("first item should be 1")
                .expectNext(2, 3)
                // test step name shown if the test step fails
                .as("second and third items should be 2 and 3")
                .expectComplete()
                .verify();
    }
}
