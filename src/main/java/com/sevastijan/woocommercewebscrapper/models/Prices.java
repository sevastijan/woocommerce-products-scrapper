package com.sevastijan.woocommercewebscrapper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Prices {
    private String price;
    @JsonProperty("regular_price")
    private String regularPrice;
    @JsonProperty("sale_price")
    private String salePrice;
    @JsonProperty("currency_code")
    private String currencyCode;
}
