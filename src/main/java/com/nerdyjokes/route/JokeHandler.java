package com.nerdyjokes.route;

import com.nerdyjokes.model.JokeResponse;
import com.nerdyjokes.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class JokeHandler {

    private static final String DEFAULT_FIRST_NAME = "Johnny";
    private static final String DEFAULT_LAST_NAME = "Foobar";

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private final JokeService jokeService;

    public Mono<ServerResponse> getJoke(final ServerRequest request) {
        final String firstName = getNameFrom(request, FIRST_NAME, DEFAULT_FIRST_NAME);
        final String lastName = getNameFrom(request, LAST_NAME, DEFAULT_LAST_NAME);
        final Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        final Mono<JokeResponse> joke = jokeService.requestRandomNerdyJoke(firstName, lastName);
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(joke, JokeResponse.class)
            .switchIfEmpty(notFound);
    }

    private String getNameFrom(final ServerRequest request, final String queryParam, final String defaultName) {
        return request.queryParam(queryParam)
            .filter(not(String::isBlank))
            .orElse(defaultName);
    }
}
