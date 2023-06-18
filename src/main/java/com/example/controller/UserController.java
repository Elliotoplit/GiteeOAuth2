package com.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.Service.UserService;
import com.example.domain.GiteeToken;
import com.example.domain.Result;
import com.example.domain.UpdateGiteeUser;
import com.example.domain.User;
import com.example.util.GiteeUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

import static com.example.Service.impl.UserServiceImpl.*;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/url")
    public String giteeLogin() {
        System.out.println("into auth");
        return "redirect:" + GiteeUtil.getUrl();
    }

    //1.用户注册
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        System.out.println("用户注册..." + user.getUsername() + " " + user.getPassword() + " " + user.getManager());
        //携带 用户名 密码 管理员
        int flag = userService.register(user);
        if (flag == USERNAME_IN) {
            return new Result(201, "账号已被注册");
        } else {
            return flag == 1 ? new Result(200, "注册成功") :
                    new Result(201, "注册失败，请检查传入数据格式");
        }
    }

    //2.用户登录
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody User user) {
        System.out.println("user = " + user);
        int flag = userService.login(user);
        //获取用户权限状态
        JSONObject jsonObject = switch (userService.loginMethod(user)) {

            case "usernameLogin" -> userService.getManagerStatus(user.getUsername());
            case "giteeLogin" -> userService.getManagerStatus(user.getGiteeId());
            default -> null;
        };


        return switch (flag) {
            case 1 -> new Result(200, jsonObject, "登录成功");
            case 0 -> new Result(201, "密码错误");
            case USERNAME_NOT_IN -> new Result(201, "账号不存在");
            case GITEE_NOT_IN -> new Result(201, "Gitee账号未绑定");
            default -> new Result(201, "未知错误");
        };
    }

    //3.Gitee 登录校验 获取 token
    @GetMapping("/auth")
    @ResponseBody
    public Result giteeAuth(@RequestParam("code") String code, HttpSession session) throws Exception {
        System.out.println("----");
        System.out.println("code = " + code);
        GiteeToken giteeToken = GiteeUtil.getToken(code);
        System.out.println("giteeToken.toString() = " + giteeToken);
        System.out.println("----");
        return giteeToken.getError() == null ?
                new Result(200, giteeToken) :
                new Result(201, giteeToken.getErrorDescription());
    }

    //4.Gitee 获取用户数据 info 用于
    @GetMapping("/gitee/info")
    @ResponseBody
    public Result getInfo(String token) throws IOException {
        //1.获取云端/本地用户同GiteeID用户
        System.out.println("获取用户数据：token = " + token);
        User userInGitee = GiteeUtil.getInfo(token);
        User userInDB = userService.selectByGiteeId(userInGitee.getGiteeId());
        //2.写入用户数据
        if (userInDB == null) {
            //2.1 如果GiteeID不对应任何账号 则要求用户先登录
            System.out.println("Gitee账号暂未绑定用户" + userInGitee.getGiteeId());
            return new Result(201, "Gitee账号暂未绑定用户,请先使用账户密码登录后绑定个人Gitee账号再试");
        } else {
            // 2.2 Gitee账号已被绑定到一个用户 则放行允许获取数据 并将最新获取到的Gitee数据更新至数据库
            System.out.println("info信息写入数据库:用户数据变化更新");
            userInGitee.setUsername(userInDB.getUsername());
            userInGitee.setPassword(userInDB.getPassword());
            userInGitee.setId(userInDB.getId());
            userInGitee.setManager(userInDB.getManager());
            userService.bindGiteeData(userInGitee);
        }
        //3.返回数据 不返回全对象(内含密码) 返回Gitee官网返回内容
        return new Result(200, userInGitee);
    }

    //5.修改Gitee账号数据
    @PutMapping("/gitee/update")
    @ResponseBody
    public Result updateInfo(@RequestBody UpdateGiteeUser updateGiteeUser) throws IOException {
        System.out.println("更新Gitee账号数据 = " + updateGiteeUser);
        Boolean status = GiteeUtil.updateGitte(updateGiteeUser);
        return status ? new Result(200, "修改成功") : new Result(201, "修改失败");
    }

    //6.刷新Gitee token

    /**
     * Refresh token
     *
     * @param refreshToken 先前 GiteeToken的refreshToken
     * @return 新 GiteeToken对象
     * @throws IOException io
     */
    @GetMapping("/refresh")
    @ResponseBody
    public Result refreshToken(String refreshToken) throws IOException {
        GiteeToken newGiteeToken = GiteeUtil.RefreshToken(refreshToken);

        //返回刷新情况
        return newGiteeToken.getError() != null ?
                new Result(201, newGiteeToken.getErrorDescription()) :
                new Result(200, newGiteeToken);
    }

    //7.修改用户密码
    @PutMapping("/password")
    @ResponseBody
    public Result changePassword(@RequestBody() JSONObject jsonObject) {
        System.out.println("修改密码：" + jsonObject.toString());
        String username = jsonObject.getString("username");
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");

        int i = userService.modifyPassword(username, oldPassword, newPassword);
        return switch (i) {
            case -1 -> new Result(201, "原密码错误");
            case 0 -> new Result(201, "密码修改失败");
            case 1 -> new Result(200, "密码修改成功");
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    //8.Gitee绑定
    @GetMapping("/gitee/bind")
    @ResponseBody
    public Result giteeBindUsername(String token, String username) throws IOException {
        //1.获取本地user和云端user
        System.out.println("绑定用户Gitee：token = " + token);
        User userInGitee = GiteeUtil.getInfo(token);
        User userInDB = userService.selectByUsername(username);//必定存在，用户已登陆
        System.out.println("userInGitee = " + userInGitee);
        System.out.println("userInDB = " + userInDB);
        //2.判定重复绑定
        if (Objects.equals(userInGitee.getGiteeId(), userInDB.getGiteeId())) {
            return new Result(200, userInGitee, "你的账号已经与此Gitee账号绑定");
        }
        //3.云端对象数据 + 本地对象数据 = 完整对象
        System.out.println("bind:信息写入数据库:用户数据变化更新");
        userInGitee.setPassword(userInDB.getPassword());
        userInGitee.setUsername(userInDB.getUsername());
        userInGitee.setId(userInDB.getId());
        userInGitee.setManager(userInDB.getManager());
        //4.不论是否绑定过，只要不是重复绑定，均继续
        return switch (userService.bindGiteeData(userInGitee)) {
            case GITEE_IN -> new Result(201, "Gitee账号已被其他用户名绑定");
            case 0 -> new Result(201, "更新失败");
            case 1 -> new Result(200, userInGitee, "更新成功");
            default -> throw new IllegalStateException("Unexpected value: " + userService.bindGiteeData(userInGitee));
        };
    }
}
