package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
//    @Insert("INSERT INTO user(id, name, email, bio, avatar_url, html_url, starred_url, created_at, updated_at) " +
//            "VALUES(#{id},#{name},#{email},#{bio},#{avatarUrl},#{htmlUrl},#{starredUrl},#{createdAt},#{updatedAt})")
//    int add(User giteeUser);

//    //注册
//    @Insert("INSERT INTO user(username, password, manager) VALUES(#{username},#{password},#{manager})")
//    int add(User user);

//    //为gitte账号设置自定义密码
//    @Update("UPDATE user SET password=#{password} WHERE id=#{id}  ")
//    int setGitteLoginPassword(@Param("password") String password, @Param("id") int id);
//
//    //修改密码
//    @Update("UPDATE user SET password = #{password2} where password=#{password1} and id=#{id}")
//    int changePassword(@Param("password1") String oldPw,@Param("password2") String newPw,@Param("id") int id);
//
//    //账号密码登录
//    @Select("SELECT * FROM user WHERE id=#{id} and password=#{password}")
//    User selectByIdAndPassword(@Param("id") int id, @Param("password") String password);
//
//    @Select("SELECT * FROM user WHERE id = #{id}")
//    User selectByGiteeId(int id);
//
//    @Delete("DELETE FROM user WHERE id=#{id}")
//    int deleteByID(int id);
//
//    @Select("SELECT name FROM user WHERE id=#{id}")
//    String selectName(int id);

}
