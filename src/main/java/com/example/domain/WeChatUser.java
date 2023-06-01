package com.example.domain;

import lombok.Data;

@Data
public class WeChatUser {
    private String openid;//用户唯一id
    private String nickname;//微信昵称
    private Integer sex;//性别
    private String language;//语言
    private String city;//城市
    private String province;//省份
    private String country;//国家
    private String headImgUrl;//头像url
    private String[] privilege;//特权
    private String unionID;//用户综合id
    private Integer errCode;//错误码
    private String errMsg;//错误信息
}
