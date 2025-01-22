package com.sevastijan.woocommercewebscrapper.services;

import com.sevastijan.woocommercewebscrapper.config.WooCommerceConfig;
import com.sevastijan.woocommercewebscrapper.models.Product;
import com.sevastijan.woocommercewebscrapper.observers.LoggingObserver;
import com.sevastijan.woocommercewebscrapper.repositories.WooCommerceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductScraperService {
    private final WooCommerceRepository repository;
    private final LoggingObserver loggingObserver;

    @PostConstruct
    public void init() {
        repository.addObserver(loggingObserver);
    }


    public void scrapeAndSave() {
        try {
            List<Product> products = repository.getAllProducts();
            repository.saveToCSV("products", products);
        } catch (Exception e) {
            System.err.println("Error while scraping: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
