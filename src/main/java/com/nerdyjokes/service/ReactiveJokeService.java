package com.nerdyjokes.service;

import com.nerdyjokes.rest.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ReactiveJokeService implements JokeService {

    private final WebClient webClient;

    @Override
    public Mono<Joke> requestRandomNerdyJoke(final String firstName, final String lastName) {
        final String queryString =
            format("/random?limitTo=nerdy&firstName=%s&lastName=%s", firstName, lastName);

        return webClient.get()
            .uri(queryString)
            .exchange()
            .flatMap(joke -> joke.bodyToMono(Joke.class));
    }
}

