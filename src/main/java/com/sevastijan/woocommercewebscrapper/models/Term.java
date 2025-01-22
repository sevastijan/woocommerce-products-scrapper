package com.sevastijan.woocommercewebscrapper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Term {
    private Long id;
    private String name;
    private String slug;
}