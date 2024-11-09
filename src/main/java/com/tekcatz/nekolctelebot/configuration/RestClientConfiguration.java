package com.tekcatz.nekolctelebot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientConfiguration {
    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
