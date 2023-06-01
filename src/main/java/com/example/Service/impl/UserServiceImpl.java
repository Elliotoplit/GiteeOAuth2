package com.example.Service.impl;

import com.example.Service.UserService;
import com.example.dao.GiteeUserDao;
import com.example.domain.GiteeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private GiteeUserDao giteeUserDao;


    @Override
    public Boolean addGiteeUser(GiteeUser user) {
        return giteeUserDao.add(user)>0;
    }

    @Override
    public GiteeUser selectByGiteeId(int id) {
        return giteeUserDao.selectByGiteeId(id);
    }

    @Override
    public Boolean deleteByID(int id) {
        return giteeUserDao.deleteByID(id)>0;
    }

    //设置用户密码
    @Override
    public Boolean setPassword(String password, int id) {
        return giteeUserDao.setGitteLoginPassword(password,id)>0;
    }

    //id&密码登录验证
    @Override
    public GiteeUser selectByIdAndPassword(int id, String password) {
        return giteeUserDao.selectByIdAndPassword(id,password);
    }

    @Override
    public Boolean changePassword(String oldPw, String newPw, int id) {
        return giteeUserDao.changePassword(oldPw,newPw,id)>0;
    }


}
