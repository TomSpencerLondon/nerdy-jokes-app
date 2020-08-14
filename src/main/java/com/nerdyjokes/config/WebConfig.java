package com.nerdyjokes.config;

import com.nerdyjokes.model.JokeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfig {

    private static final String API_PATH = "/api/v1";

    @Bean
    public RouterFunction<ServerResponse> jokeRouterFunction(final JokeHandler jokeHandler) {
        return route(GET(API_PATH)
            .and(accept(APPLICATION_JSON)), jokeHandler::getJoke);
    }
}
