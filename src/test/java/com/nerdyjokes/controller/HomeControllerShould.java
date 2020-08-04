package com.nerdyjokes.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(HomeController.class)
class HomeControllerShould {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void return_success_code_for_index_page() {
        webTestClient.get()
            .uri("/")
            .accept(MediaType.TEXT_HTML)
            .exchange()
            .expectStatus()
            .is2xxSuccessful();
    }
}
