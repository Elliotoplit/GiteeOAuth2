package com.example.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.domain.User;

public interface UserService {

    //1.用户名存在性校验
    int checkAccountExistence(String username);

    //2.GiteeID存在性校验
    int checkAccountExistence(Integer giteeID);

    //3.注册
    int register(User user);

    //4.登录
    int login(User user);

    //5.获取数据库 Gitee 账号数据：By id
    User selectByGiteeId(int id);

    //6.绑定用户 Gitee 数据到原账号
    int bindGiteeData(User user);

    //7.按用户名查找用户数据
    User selectByUsername(String username);

    //8.修改密码
    int modifyPassword(String username, String oldPassword, String newPassword);

    //9.获取用户权限状态 用于登录返回结果
    JSONObject getManagerStatus(String username);

    JSONObject getManagerStatus(Integer GiteeID);

    //10.获取登录方式
    String loginMethod(User user);
}
