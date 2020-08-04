package com.nerdyjokes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public Mono<String> getHomePage() {
        return Mono.just("index");
    }
}
