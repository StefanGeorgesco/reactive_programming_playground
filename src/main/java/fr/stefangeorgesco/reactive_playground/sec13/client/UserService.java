package fr.stefangeorgesco.reactive_playground.sec13.client;

import reactor.util.context.Context;

import java.util.Map;
import java.util.function.UnaryOperator;

public class UserService {

    private UserService() {
    }

    private static final Map<String, String> USER_CATEGORY_MAP = Map.of(
            "sam", "standard",
            "mike", "prime"
    );

    static UnaryOperator<Context> userCategoryContext = ctx ->
            ctx.<String>getOrEmpty("user")
                    .filter(USER_CATEGORY_MAP::containsKey)
                    .map(USER_CATEGORY_MAP::get)
                    .map(category -> ctx.put("category", category))
                    .orElse(Context.empty());
}
