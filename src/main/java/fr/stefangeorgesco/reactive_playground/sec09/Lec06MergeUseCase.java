package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec09.helper.Kayak;

import java.time.Duration;

public class Lec06MergeUseCase {

    public static void main(String[] args) {

        Kayak.getFlights()
                .subscribe(Util.subscriber("flights subscriber"));

        Util.sleep(Duration.ofSeconds(3));
    }
}
