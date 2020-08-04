package com.nerdyjokes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import static com.nerdyjokes.logging.LogFilters.logRequest;
import static com.nerdyjokes.logging.LogFilters.logResponse;

@Configuration
public class WebConfig {

    private static final String JOKES_API_URL = "https://api.icndb.com/jokes";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl(JOKES_API_URL)
            .clientConnector(new ReactorClientHttpConnector())
            .filters(exchangeFilterFunctions -> {
                exchangeFilterFunctions.add(logRequest());
                exchangeFilterFunctions.add(logResponse());
            }).build();
    }
}
