package fr.stefangeorgesco.reactive_playground.sec06;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec06.assignment.InventoryService;
import fr.stefangeorgesco.reactive_playground.sec06.assignment.OrderServiceImpl;
import fr.stefangeorgesco.reactive_playground.sec06.assignment.RevenueService;

public class Lec06Assignment {

    public static void main(String[] args) throws InterruptedException {
        var orderService = new OrderServiceImpl();
        var inventoryService = new InventoryService();
        var revenueService = new RevenueService();

        orderService.getOrderStream().subscribe(inventoryService::consume);
        orderService.getOrderStream().subscribe(revenueService::consume);

        inventoryService.stream().subscribe(Util.subscriber("inventory"));
        revenueService.stream().subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(30);
    }
}
