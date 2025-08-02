package com.example.urlshortend;

@Data
public class CreateShortUrlRequest {
    private String url;
    private Integer validity; // optional
    private String shortcode; // optional
}
