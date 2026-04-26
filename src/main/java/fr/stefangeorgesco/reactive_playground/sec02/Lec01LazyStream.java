package fr.stefangeorgesco.reactive_playground.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/*
    If we do not have the terminal operator, then stream operators will not execute
 */

public class Lec01LazyStream {

    private static  final Logger logger = LoggerFactory.getLogger(Lec01LazyStream.class);

    public static void main(String[] args) {

        var list = Stream.of(1)
                .peek(i -> logger.info("received {}", i))
                .toList();

        logger.info("list is {}", list);
    }
}
