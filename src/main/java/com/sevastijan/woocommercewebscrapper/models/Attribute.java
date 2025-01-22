package com.sevastijan.woocommercewebscrapper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Attribute {
    private Long id;
    private String name;
    private String taxonomy;
    @JsonProperty("has_variations")
    private boolean hasVariations;
    private List<Term> terms;
}
