package com.sevastijan.woocommercewebscrapper.repositories;

import com.sevastijan.woocommercewebscrapper.models.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    void saveToCSV(String filename, List<Product> products);

}
