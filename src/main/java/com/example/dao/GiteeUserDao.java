package com.example.dao;

import com.example.domain.GiteeUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
public interface GiteeUserDao {
    @Insert("INSERT INTO giteeuser(id, name, email, bio, avatar_url, html_url, starred_url, created_at, updated_at) " +
            "VALUES(#{id},#{name},#{email},#{bio},#{avatarUrl},#{htmlUrl},#{starredUrl},#{createdAt},#{updatedAt})")
    int add(GiteeUser giteeUser);

    //为gitte账号设置自定义密码
    @Update("UPDATE giteeuser SET password=#{password} WHERE id=#{id}  ")
    int setGitteLoginPassword(@Param("password") String password,@Param("id") int id);

    //修改密码
    @Update("UPDATE giteeuser SET password = #{password2} where password=#{password1} and id=#{id}")
    int changePassword(@Param("password1") String oldPw,@Param("password2") String newPw,@Param("id") int id);
    //账号密码登录
    @Select("SELECT * FROM giteeuser WHERE id=#{id} and password=#{password}")
    GiteeUser selectByIdAndPassword(@Param("id") int id,@Param("password") String password);

    @Select("SELECT * FROM giteeuser WHERE id = #{id}")
    GiteeUser selectByGiteeId(int id);

    @Delete("DELETE FROM giteeuser WHERE id=#{id}")
    int deleteByID(int id);

}
