package com.sevastijan.woocommercewebscrapper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String slug;
    private Long parent;
    private String type;
    private String variation;
    private String permalink;
    private String sku;
    @JsonProperty("short_description")
    private String shortDescription;
    private String description;
    @JsonProperty("on_sale")
    private boolean onSale;
    private Prices prices;
    @JsonProperty("price_html")
    private String priceHtml;
    @JsonProperty("average_rating")
    private String averageRating;
    @JsonProperty("review_count")
    private int reviewCount;
    private List<Image> images;
    private List<Category> categories;
    private List<Attribute> attributes;
    private List<Object> variations;
    @JsonProperty("has_options")
    private boolean hasOptions;
    @JsonProperty("is_purchasable")
    private boolean isPurchasable;
    @JsonProperty("is_in_stock")
    private boolean isInStock;
    @JsonProperty("is_on_backorder")
    private boolean isOnBackorder;
    @JsonProperty("low_stock_remaining")
    private Integer lowStockRemaining;
    @JsonProperty("sold_individually")
    private boolean soldIndividually;
}
