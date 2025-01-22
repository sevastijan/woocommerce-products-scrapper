package com.sevastijan.woocommercewebscrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.sevastijan.woocommercewebscrapper.services.ProductScraperService;

@SpringBootApplication
public class WoocommerceWebScrapperApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WoocommerceWebScrapperApplication.class);
        app.setRegisterShutdownHook(false);

        try (ConfigurableApplicationContext context = app.run(args)) {
            ProductScraperService scraperService = context.getBean(ProductScraperService.class);
            scraperService.scrapeAndSave();
        }

    }

}
