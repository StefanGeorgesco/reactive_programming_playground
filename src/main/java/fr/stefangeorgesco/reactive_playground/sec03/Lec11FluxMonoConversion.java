package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec11FluxMonoConversion {

    public static void main(String[] args) {
        // Mono to Flux
        monoToFlux();

        // Flux to Mono
        var flux = Flux.range(1, 10);
        Mono.from(flux)
                .subscribe(Util.subscriber("mono from flux subscriber"));
    }

    private static void monoToFlux() {
        var mono = getUsername(1);
        save(Flux.from(mono));
    }

    @SuppressWarnings("SameParameterValue")
    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new IllegalArgumentException("invalid user id"));
        };
    }

    private  static void save(Flux<String> flux) {
        flux
                .subscribe(Util.subscriber("save subscriber"));
    }
}
