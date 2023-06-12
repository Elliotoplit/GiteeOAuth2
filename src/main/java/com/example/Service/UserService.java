package com.example.Service;

import com.example.domain.GiteeUser;

public interface UserService {


    Boolean addGiteeUser(GiteeUser user);

    GiteeUser selectByGiteeId(int id);

    Boolean deleteByID(int id);

    Boolean setPassword(String password, int id);

    GiteeUser selectByIdAndPassword(int id, String password);

    Boolean changePassword(String oldPw, String newPw, int id);

    String selectNameById(Integer id);
}
