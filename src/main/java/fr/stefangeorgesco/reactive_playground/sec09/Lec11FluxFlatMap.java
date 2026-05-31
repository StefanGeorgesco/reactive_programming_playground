package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec09.applications.OrderService;
import fr.stefangeorgesco.reactive_playground.sec09.applications.User;
import fr.stefangeorgesco.reactive_playground.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec11FluxFlatMap {

    public static void main(String[] args) throws InterruptedException {

        /*
            Get all the orders from order service!
         */

        UserService.getAllUsers()
                .map(User::id)
                .flatMap(OrderService::getUserOrders)
                .subscribe(Util.subscriber("all orders subscriber"));

        Util.sleepSeconds(5);

    }
}
