package com.example.domain;

import lombok.Data;

import java.util.Date;

@Data
public class GiteeUser {
    private Integer id;//GiteeID
    private String password;//用户自定义密码
    private String name;//用户名
    private String email;//邮箱
    private String bio;//个人介绍
    private String avatarUrl;//用户头像
    private String htmlUrl;//json-用户主页
    private String starredUrl;//json-用户收藏
    private String createdAt;//账号创建日期
    private Date updatedAt;//Gitee最近项目活跃时间
}
