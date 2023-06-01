package com.example.domain;

import lombok.Data;

@Data
public class GiteeToken {
    private String accessToken;//token
    private String tokenType;//token类型
    private String expiresIn;//token过期时长 86400s = 1day
    private String refreshToken;//刷新token
    private String scope;//权限范围
    private String createdAt;//token创建时间戳
    private String error; //错误
    private String errorDescription;//错误信息
}
