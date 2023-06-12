package com.example.domain;

import lombok.Data;

@Data
public class News {
    private Integer id;
    private String title;
    private String date;
    private String url;
    private String content;
}
