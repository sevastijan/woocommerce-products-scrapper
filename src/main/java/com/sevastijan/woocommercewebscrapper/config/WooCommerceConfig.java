package com.sevastijan.woocommercewebscrapper.config;

public class WooCommerceConfig {
    private final String baseUrl;
    private final int perPage;

    private WooCommerceConfig(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.perPage = builder.perPage;
    }

    public static class Builder {
        private String baseUrl;
        private int perPage;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        public WooCommerceConfig build() {
            if (baseUrl == null || baseUrl.isEmpty()) {
                throw new IllegalStateException("baseUrl is required");
            }
            if (perPage <= 0) {
                throw new IllegalStateException("perPage must be greater than 0");
            }
            return new WooCommerceConfig(this);
        }
    }

    public String buildUrl(int page) {
        return String.format("%s/wp-json/wc/store/v1/products?per_page=%d&page=%d",
                baseUrl, perPage, page);
    }
}
