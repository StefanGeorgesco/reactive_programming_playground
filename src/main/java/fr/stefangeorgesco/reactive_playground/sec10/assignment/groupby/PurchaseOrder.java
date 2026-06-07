package fr.stefangeorgesco.reactive_playground.sec10.assignment.groupby;

import fr.stefangeorgesco.reactive_playground.common.Util;

public record PurchaseOrder(String item, String category, int price) {

    public static PurchaseOrder create() {
        String item = Util.faker().commerce().productName();
        String category = Util.faker().commerce().department();
        int price = Util.faker().random().nextInt(10, 100);
        return new PurchaseOrder(item, category, price);
    }
}
