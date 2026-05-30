package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec09.applications.OrderService;
import fr.stefangeorgesco.reactive_playground.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
    Mono is supposed to be 1 item - what if the flatMap returns multiple items!?
 */
public class Lec10MonoFlapMapMany {

    public static void main(String[] args) throws InterruptedException {

        /*
            We have username
            get all user orders!
         */

        UserService.getUserId("Mike")
                .flatMapMany(OrderService::getUserOrders)
                .subscribe(Util.subscriber("user orders subscriber"));

        Util.sleepSeconds(2);
    }
}
