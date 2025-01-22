package com.sevastijan.woocommercewebscrapper.config;

import com.sevastijan.woocommercewebscrapper.observers.LoggingObserver;
import com.sevastijan.woocommercewebscrapper.repositories.WooCommerceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScraperConfig {

    @Value("${woocommerce.base-url}")
    private String baseUrl;

    @Bean
    public WooCommerceConfig wooCommerceConfig() {
        return new WooCommerceConfig.Builder()
                .baseUrl(baseUrl)
                .perPage(100)
                .build();
    }

    @Bean
    public WooCommerceRepository wooCommerceRepository(WooCommerceConfig config) {
        return new WooCommerceRepository(config);
    }

    @Bean
    public LoggingObserver loggingObserver() {
        return new LoggingObserver();
    }
}
