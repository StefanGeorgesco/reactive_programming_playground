package fr.stefangeorgesco.reactive_playground.tests;

/*
    "assertNext" is a method in StepVerifier
    assertNext = consumeNextWith
    We can also collect all the items and test.
 */

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Lec05AssertNextTest {

    record Book(int id, String author, String title) {
    }

    private Flux<Book> getBooks() {
        return Flux.range(1, 3)
                .map(i -> new Book(i, Util.faker().book().author(), Util.faker().book().title()));
    }

    @Test
    void assertNextTest() {
        StepVerifier.create(getBooks())
                .assertNext(b -> assertEquals(1, b.id()))
                .thenConsumeWhile(book -> Objects.nonNull(book.title()))
                .verifyComplete();
    }

    @Test
    void collectAllAndTest() {
        StepVerifier.create(getBooks().collectList())
                .assertNext(bookList -> assertEquals(3, bookList.size()))
                .verifyComplete();
    }
}
