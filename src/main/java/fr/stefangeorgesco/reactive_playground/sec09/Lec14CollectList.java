package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

public class Lec14CollectList {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .collectList()
                .subscribe(Util.subscriber("collectList subscriber"));
    }
}
