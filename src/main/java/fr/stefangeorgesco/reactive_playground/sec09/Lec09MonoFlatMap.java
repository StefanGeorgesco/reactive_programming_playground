package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec09.applications.PaymentService;
import fr.stefangeorgesco.reactive_playground.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec09MonoFlatMap {

    public static void main(String[] args) {

        /*
            We have username.
            Get user account balance.
         */

        UserService.getUserId("Jake")
                .flatMap(PaymentService::getUserBalance)
                .subscribe(Util.subscriber("user balance subscriber"));
    }
}
