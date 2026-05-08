package fr.stefangeorgesco.reactive_playground.sec02;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec07MonoFromRunnable {

    private static final Logger logger = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

    public static void main(String[] args) {
        getProductName(1).subscribe(Util.subscriber());
        getProductName(2).subscribe(Util.subscriber());
    }

    @SuppressWarnings("SameParameterValue")
    private static Mono<String> getProductName(int productId) {
        if (productId == 1) {
            // Execution of supplier is deferred until the subscriber subscribes
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }
        // Execution of runnable is deferred until the subscriber subscribes
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    private static void notifyBusiness(int productId) {
        logger.info("notifying business about unavailable product {}", productId);
    }

}
