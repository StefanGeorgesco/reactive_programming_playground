package fr.stefangeorgesco.reactive_playground.sec02;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Mono;

/*
 * Emitting empty / error
 */

public class Lec04MonoEmptyError {

    public static void main(String[] args) {
        getUsername(2).subscribe(Util.subscriber());
    }

    @SuppressWarnings("SameParameterValue")
    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new IllegalArgumentException("invalid user id"));
        };
    }
}
