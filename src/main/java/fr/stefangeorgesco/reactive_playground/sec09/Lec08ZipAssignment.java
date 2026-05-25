package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec09.assignment.ExternalServiceClient;

import java.time.Duration;

public class Lec08ZipAssignment {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        for (int i = 1; i <= 10; i++) {
            client.getProduct(i)
                    .subscribe(Util.subscriber("product subscriber " + i));
        }

        Util.sleep(Duration.ofSeconds(2));
    }
}
