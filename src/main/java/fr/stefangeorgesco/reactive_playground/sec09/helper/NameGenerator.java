package fr.stefangeorgesco.reactive_playground.sec09.helper;

import fr.stefangeorgesco.reactive_playground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);
    private final List<String> cache = new ArrayList<>(); // for demo purpose only

    public Flux<String> generateNames() {
        return Flux.generate(sink -> {
                    log.info("generating name");
                    Util.sleep(Duration.ofSeconds(1));
                    var name = Util.faker().name().firstName();
                    cache.add(name);
                    sink.next(name);
                })
                .startWith(cache)
                .cast(String.class);
    }
}
