package fr.stefangeorgesco.reactive_playground.sec04;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec08GenerateWithState {

    public static void main(String[] args) {

        Flux.generate(
                        () -> 0,
                        (counter, synchronousSink) -> {
                            var country = Util.faker().country().name();
                            synchronousSink.next(country);
                            counter++;
                            if (counter == 30 || country.equalsIgnoreCase("France")) {
                                synchronousSink.complete();
                            }
                            return counter;
                        }
                )
                .subscribe(Util.subscriber("Subscriber"));
    }
}
