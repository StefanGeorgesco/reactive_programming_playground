package fr.stefangeorgesco.reactive_playground.sec10;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec10.assignment.buffer.BookOrder;
import fr.stefangeorgesco.reactive_playground.sec10.assignment.buffer.RevenueReport;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Lec02BufferAssignment {

    public static void main(String[] args) {

        var allowedGenres = Set.of("Science fiction", "Fantasy", "Suspense/Thriller");

        bookOrderStream()
                .filter(bookOrder -> allowedGenres.contains(bookOrder.genre()))
                .buffer(Duration.ofSeconds(5))
                .map(Lec02BufferAssignment::generateReport)
                .subscribe(Util.subscriber("book revenue report subscriber"));

        Util.sleep(Duration.ofSeconds(60));
    }

    private static Flux<BookOrder> bookOrderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> BookOrder.create());
    }

    private static RevenueReport generateReport(List<BookOrder> bookOrders) {
        Map<String, Integer> revenue = bookOrders.stream()
                .collect(Collectors.groupingBy(
                                BookOrder::genre,
                                Collectors.summingInt(BookOrder::price)
                        )
                );

        return new RevenueReport(LocalTime.now(), revenue);
    }
}
