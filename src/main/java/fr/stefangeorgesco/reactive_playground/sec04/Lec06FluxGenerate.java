package fr.stefangeorgesco.reactive_playground.sec04;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Flux generate
    - invokes the given lambda expression again and again based on downstream demand
    - we can emit only one value at a time
    - will stop when the complete method is invoked
    - will stop when the error method is invoked
    - will stop when the downstream cancels
 */

public class Lec06FluxGenerate {

    private static final Logger logger = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    public static void main(String[] args) {

        Flux.generate(synchronousSink -> {
                    logger.info("Lambda invoked");
                    // next can be called only once on each synchronousSink
                    synchronousSink.next(1);
                })
                .take(5)
                .subscribe(Util.subscriber("Subscriber"));
    }
}
