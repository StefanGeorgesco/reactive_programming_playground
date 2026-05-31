package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec09.assignment.ExternalServiceClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec12FluxFlatMapAssignment {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        Flux.range(1, 10)
                .flatMap(client::getProduct)
                .subscribe(Util.subscriber("product subscriber"));

        Util.sleep(Duration.ofSeconds(2));
    }
}
