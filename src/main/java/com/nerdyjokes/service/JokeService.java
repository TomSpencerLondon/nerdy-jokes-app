package com.nerdyjokes.service;

import com.nerdyjokes.rest.Joke;
import reactor.core.publisher.Mono;

public interface JokeService {

    Mono<Joke> requestRandomNerdyJoke(String firstName, String lastName);
}
