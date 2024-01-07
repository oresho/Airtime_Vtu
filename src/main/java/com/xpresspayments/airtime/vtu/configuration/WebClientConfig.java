package com.xpresspayments.airtime.vtu.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    // Set up WebClient Bean for Application
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
