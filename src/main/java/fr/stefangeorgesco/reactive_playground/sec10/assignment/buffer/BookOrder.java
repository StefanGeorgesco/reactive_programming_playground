package fr.stefangeorgesco.reactive_playground.sec10.assignment.buffer;

import fr.stefangeorgesco.reactive_playground.common.Util;

public record BookOrder(String genre, String title, Integer price) {

    public static BookOrder create() {
        var book = Util.faker().book();
        var price = Util.faker().random().nextInt(5, 40);
        return new BookOrder(book.genre(), book.title(), price);
    }
}
