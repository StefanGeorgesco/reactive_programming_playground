package fr.stefangeorgesco.reactive_playground.sec05.assignment;

import fr.stefangeorgesco.reactive_playground.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ProductServiceImpl extends AbstractHttpClient implements ProductService {

    @Override
    public Mono<String> getProductName(int productId) {
        var defaultPath = "/demo03/product/" + productId;
        var timeoutPath = "/demo03/timeout-fallback/product/" + productId;
        var emptyPath = "/demo03/empty-fallback/product/" + productId;

        return this.getProductName(defaultPath)
                .timeout(Duration.ofSeconds(2), getProductName(timeoutPath))
                .switchIfEmpty(getProductName(emptyPath));
    }

    /*
        Private method
     */

    private Mono<String> getProductName(String path) {
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }
}
