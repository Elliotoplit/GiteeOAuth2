package com.example.domain;

import lombok.Data;

@Data
public class WeChatToken {
    private String accessToken;//网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
    private String expiresIn;//access_token接口调用凭证超时时间
    private String refreshToken;//用户刷新access_token
    private String openid;//用户唯一标识
    private String scope;//用户授权的作用域
    private String isSnapshotUser;//是否为快照页模式虚拟账号
    private String unionId;//用户统一标识（针对一个微信开放平台帐号下的应用，同一用户的 unionid 是唯一的），只有当scope为"snsapi_userinfo"时返回
    private Integer errCode;//错误码
    private String errMsg;//错误信息
}
