package com.sevastijan.woocommercewebscrapper.observers;

import com.sevastijan.woocommercewebscrapper.models.Product;

import java.util.List;

public class LoggingObserver implements ProductObserver{

    @Override
    public void onProductsFetched(List<Product> products) {
        System.out.println("PRODUCTS FETCHED: " + products.size());
    }

    @Override
    public void onError(Exception e) {
        System.err.println("ERROR ON FETCH: " + e.getMessage());

    }
}
