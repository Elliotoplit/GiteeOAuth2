package com.example.Service.impl;

import com.example.Service.UserService;
import com.example.dao.UserDao;
import com.example.domain.GiteeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Boolean addGiteeUser(GiteeUser user) {
        return userDao.add(user) > 0;
    }

    @Override
    public GiteeUser selectByGiteeId(int id) {
        return userDao.selectByGiteeId(id);
    }

    @Override
    public Boolean deleteByID(int id) {
        return userDao.deleteByID(id) > 0;
    }

    //设置用户密码
    @Override
    public Boolean setPassword(String password, int id) {
        return userDao.setGitteLoginPassword(password, id) > 0;
    }

    //id&密码登录验证
    @Override
    public GiteeUser selectByIdAndPassword(int id, String password) {
        return userDao.selectByIdAndPassword(id, password);
    }

    //修改密码
    @Override
    public Boolean changePassword(String oldPw, String newPw, int id) {
        return userDao.changePassword(oldPw, newPw, id) > 0;
    }

    //通过id获取用户姓名
    public String selectNameById(Integer id) {
        return userDao.selectName(id);
    }


}
