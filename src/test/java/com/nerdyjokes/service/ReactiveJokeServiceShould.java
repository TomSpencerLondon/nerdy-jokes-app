package com.nerdyjokes.service;

import com.nerdyjokes.rest.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static java.lang.String.format;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveJokeServiceShould {

    private ReactiveJokeService jokeService;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;

    @Mock
    private ClientResponse clientResponse;

    @BeforeEach
    void setUp() {
        jokeService = new ReactiveJokeService(webClientMock);
    }

    @Test
    void return_nerdy_joke_when_requested_from_external_jokes_resource() {
        final Joke mockJoke = new Joke("success", new Joke.Value(1, "joke", List.of("nerdy")));

        final String firstName = "John";
        final String lastName = "Doe";
        final String mockQueryString = format("/random?limitTo=nerdy&firstName=%s&lastName=%s", firstName, lastName);

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(mockQueryString)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.exchange()).thenReturn(Mono.just(clientResponse));
        when(clientResponse.bodyToMono(Joke.class)).thenReturn(Mono.just(mockJoke));

        Mono<Joke> jokeMono = jokeService.requestRandomNerdyJoke(firstName, lastName);

        StepVerifier.create(jokeMono)
            .expectNextMatches(joke -> joke.equals(mockJoke))
            .verifyComplete();
    }
}
