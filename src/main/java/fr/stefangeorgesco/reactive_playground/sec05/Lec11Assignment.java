package fr.stefangeorgesco.reactive_playground.sec05;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec05.assignment.ProductServiceImpl;

public class Lec11Assignment {

    public static void main(String[] args) throws InterruptedException {

        var service = new ProductServiceImpl();

        for (int i = 1; i <= 4; i++) {
            service.getProductName(i)
                    .subscribe(Util.subscriber("Product subscriber " + i));
        }

        Util.sleepSeconds(3);
    }
}
