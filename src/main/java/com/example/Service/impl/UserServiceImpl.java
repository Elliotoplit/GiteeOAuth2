package com.example.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.Service.UserService;
import com.example.dao.UserDao;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    public static final int USERNAME_IN = 20011;//原始id存在
    public static final int USERNAME_NOT_IN = 20010;//原始id不存在
    public static final int GITEE_IN = 20021;//Gitee已绑定
    public static final int GITEE_NOT_IN = 20020;//Gitee未绑定

    //1.存在校验1 username
    @Override
    public int checkAccountExistence(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userDao.selectList(wrapper).size() > 0 ? USERNAME_IN : USERNAME_NOT_IN;
    }

    //1.存在校验2 giteeID
    @Override
    public int checkAccountExistence(Integer giteeID) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getGiteeId, giteeID);
        return userDao.selectList(wrapper).size() > 0 ? GITEE_IN : GITEE_NOT_IN;
    }

    //2.用户注册
    @Override
    public int register(User user) {
        int flag = checkAccountExistence(user.getUsername());
        if (flag == USERNAME_IN) {
            return USERNAME_IN;//用户名存在
        } else {
            return userDao.insert(user);
        }
    }

    //3.用户登录
    @Override
    public int login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        String username = user.getUsername();
        Integer giteeId = user.getGiteeId();
        String password = user.getPassword();
        if (username != null) {
            //用户名登录 检查用户名存在性
            System.out.println("username登录 = " + username);
            if (checkAccountExistence(username) == USERNAME_NOT_IN) {
                return USERNAME_NOT_IN;
            }
            wrapper.eq(User::getUsername, username)
                    .eq(User::getPassword, password);
            return userDao.selectOne(wrapper) != null ? 1 : 0;
        } else if (giteeId != null) {
            //GiteeID登录 检查Gitee账号是否绑定
            System.out.println("giteeId登录 = " + giteeId);
            if (checkAccountExistence(giteeId) == GITEE_NOT_IN) {
                return GITEE_NOT_IN;
            }
            wrapper.eq(User::getGiteeId, giteeId)
                    .eq(User::getPassword, password);
            return userDao.selectOne(wrapper) != null ? 1 : 0;
        }
        return -1;
    }

    //4.获取数据库中Gitee账号数据
    @Override
    public User selectByGiteeId(int giteeId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getGiteeId, giteeId);
        return userDao.selectList(wrapper).get(0);
    }

    //5.绑定Gitee数据到账号
    @Override
    public int bindGiteeData(User user) {
        //查询当前完整对象的GiteeId是否被绑定过
        User u = userDao.selectOne(new LambdaUpdateWrapper<User>().eq(User::getGiteeId, user.getGiteeId()));
        if (u != null) {
            return GITEE_IN; //用户已经被绑定
        } else {
            return userDao.updateById(user);
        }
    }

    //6.按照username查询数据
    @Override
    public User selectByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userDao.selectOne(wrapper);
    }

    //7.修改密码
    @Override
    public int modifyPassword(String username, String oldPassword, String newPassword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username).eq(User::getPassword, oldPassword);
        if (userDao.selectOne(wrapper) == null) {
            return -1;//原密码输入错误
        }
        LambdaUpdateWrapper<User> wrapper1 = new LambdaUpdateWrapper<>();
        User user = selectByUsername(username);//原密码正确 获取原数据 设置新密码
        user.setPassword(newPassword);
        return userDao.updateById(user);
    }

    //8.返回用户权限状态 usernmae
    @Override
    public JSONObject getManagerStatus(String username) {
        JSONObject jsonObject = new JSONObject();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userDao.selectOne(wrapper);
        jsonObject.put("manager", user.getManager());
        return jsonObject;
    }

    //8.返回用户权限状态 giteeID
    @Override
    public JSONObject getManagerStatus(Integer GiteeID) {
        JSONObject jsonObject = new JSONObject();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getGiteeId, GiteeID);
        User user = userDao.selectOne(wrapper);
        jsonObject.put("manager", user.getManager());
        return jsonObject;
    }

    //9获取登录方式
    @Override
    public String loginMethod(User user) {
        String username = user.getUsername();
        Integer giteeId = user.getGiteeId();
        String password = user.getPassword();
        if (username != null) {
            return "usernameLogin";
        } else {
            return "giteeLogin";
        }
    }
}
