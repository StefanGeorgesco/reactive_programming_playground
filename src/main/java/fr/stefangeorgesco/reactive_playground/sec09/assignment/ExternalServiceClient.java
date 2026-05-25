package fr.stefangeorgesco.reactive_playground.sec09.assignment;

import fr.stefangeorgesco.reactive_playground.common.AbstractHttpClient;
import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<Product> getProduct(int productId) {
        return Mono.zip(
                        getProductName(productId),
                        getProductPrice(productId),
                        getProductReview(productId)
                )
                .map(tuple3 -> new Product(tuple3.getT1(), tuple3.getT2(), tuple3.getT3()));

    }

    /*
     * Private methods
     */

    private Mono<String> getProductName(int productId) {
        return this.get("/demo05/product/" + productId);
    }

    private Mono<String> getProductPrice(int productId) {
        return this.get("/demo05/price/" + productId);
    }

    private Mono<String> getProductReview(int productId) {
        return this.get("/demo05/review/" + productId);
    }

    private Mono<String> get(String path) {
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .transform(Util.fluxLogger("get " + path))
                .next()
                // delegate to a scheduler to avoid blocking the IO non-blocking thread
                .publishOn(Schedulers.boundedElastic());
    }
}
