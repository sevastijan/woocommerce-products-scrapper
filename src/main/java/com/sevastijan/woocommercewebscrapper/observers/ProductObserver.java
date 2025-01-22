package com.sevastijan.woocommercewebscrapper.observers;

import com.sevastijan.woocommercewebscrapper.models.Product;

import java.util.List;

public interface ProductObserver {
    void onProductsFetched(List<Product> products);
    void onError(Exception e);

}
