package com.sevastijan.woocommercewebscrapper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class Image {
    private Long id;
    private String src;
    private String thumbnail;
    private String srcset;
    private String sizes;
    private String name;
    private String alt;
}
