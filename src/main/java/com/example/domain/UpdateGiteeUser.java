package com.example.domain;

import lombok.Data;

@Data
public class UpdateGiteeUser {
    private String access_token;
    private String name;
    private String bio;
}
