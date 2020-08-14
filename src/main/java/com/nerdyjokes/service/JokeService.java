package com.nerdyjokes.service;

import com.nerdyjokes.model.JokeResponse;
import reactor.core.publisher.Mono;

public interface JokeService {

    Mono<JokeResponse> requestRandomNerdyJoke(String firstName, String lastName);
}
