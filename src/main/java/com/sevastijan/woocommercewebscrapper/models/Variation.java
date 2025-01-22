package com.sevastijan.woocommercewebscrapper.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Variation {
    private Long id;
    private String name;
}