package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;//系统分配ID
    private String username;//唯一用户名
    @TableField(select = false)
    private String password;//用户自定义密码
    private Integer manager;//管理员：2 普通用户1
    private Integer giteeId;//gitee账号
    private String giteeName;//gitee用户名
    private String email;//邮箱
    private String bio;//gitee个人介绍
    private String avatarUrl;//gitee用户头像
    private String htmlUrl;//json-gitee用户主页
    private Date updatedAt;//Gitee最近项目活跃时间
    private String createdAt;//gitee账号创建日期
}
