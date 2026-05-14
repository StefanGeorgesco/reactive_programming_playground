package fr.stefangeorgesco.reactive_playground.sec04.helper;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {

    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> fluxSink) {
        this.fluxSink = fluxSink;
    }

    public void generate() {
        if (this.fluxSink == null) {
            return;
        }
        this.fluxSink.next(Util.faker().name().firstName());
    }
}
