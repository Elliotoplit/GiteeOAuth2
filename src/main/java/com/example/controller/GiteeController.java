package com.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.Service.UserService;
import com.example.domain.GiteeToken;
import com.example.domain.GiteeUser;
import com.example.domain.Result;
import com.example.domain.UpdateGiteeUser;
import com.example.util.GiteeUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/gitee")
@CrossOrigin
public class GiteeController {

    @Autowired
    UserService userService;

//    /**
//     * 拼接访问地址
//     * @return 跳转到拼接了clientID的url
//     */
//    @GetMapping("/login")
//    public String giteeLogin() {
//        System.out.println("into auth");
//        return "redirect:" + GiteeUtil.getUrl();
//    }

    /**
     * Gitee 登录校验
     *
     * @param code 授权校验码
     * @return res
     * @throws Exception io
     */
    @GetMapping("/auth")
    @ResponseBody
    public Result giteeAuth(@RequestParam("code") String code, HttpSession session) throws Exception {
        System.out.println("----");
        System.out.println("code = " + code);
        GiteeToken giteeToken = GiteeUtil.getToken(code);
        System.out.println("giteeToken.toString() = " + giteeToken);
        System.out.println("----");
        return giteeToken.getError() ==null ?
                new Result(200,giteeToken):
                new Result(201,giteeToken.getErrorDescription());
    }

    /**
     * Refresh token
     * @param refreshToken 先前 GiteeToken的refreshToken
     * @return 新 GiteeToken对象
     * @throws IOException io
     */
    @GetMapping("/refresh")
    @ResponseBody
    public Result refreshToken(String refreshToken) throws IOException {
        GiteeToken newGiteeToken = GiteeUtil.RefreshToken(refreshToken);

        //返回刷新情况
        return newGiteeToken.getError()!=null?
                new Result(201,newGiteeToken.getErrorDescription()):
                new Result(200,newGiteeToken);
    }

    /**
     * Get gitee user information
     * @param token token
     * @return res
     * @throws IOException io
     */
    @GetMapping("/info")
    @ResponseBody
    public Result getInfo(String token) throws IOException {
        System.out.println("info-token = " + token);
        GiteeUser user = GiteeUtil.getInfo(token);
        //如果数据库中没有该 Gitee 用户数据则添加数据到 Gitee 用户表
        GiteeUser existence = userService.selectByGiteeId(user.getId());

        if (existence != null) {
            //用户已经注册
            user.setPassword(existence.getPassword());
            userService.deleteByID(user.getId());
            System.out.println("info信息写入数据库:用户数据变化更新");
            userService.addGiteeUser(user);
        } else {
            //用户未注册
            System.out.println("info信息写入数据库:from用户首次登录");
            userService.addGiteeUser(user);
        }

        return new Result(200, user);
    }

    //更新用户Gitee数据
    @PostMapping("/update")
    @ResponseBody
    public Result updateInfo(@RequestBody UpdateGiteeUser updateGiteeUser) throws IOException {
        System.out.println("updateGiteeUser！！ = " + updateGiteeUser);
        Boolean status = GiteeUtil.updateGitte(updateGiteeUser);
        return status?new Result(200,"修改成功"):new Result(201,"修改失败");
    }

    //设置用户密码
    @PostMapping("/password")
    @ResponseBody
    public Result setPassword(@RequestBody JSONObject idAndPassword){
        String id = idAndPassword.getString("id");
        String password = idAndPassword.getString("password");
        System.out.println("xxxx:"+id+" "+password);
        Boolean flag = userService.setPassword(password, Integer.parseInt(id));
        return flag?new Result(200,"设置密码成功"):new Result(201,"设置密码失败");
    }

    //密码id登录的验证
    @PostMapping("/login")
    @ResponseBody
    public Result passwordLogin(@RequestBody JSONObject idAndPassword){
        String id = idAndPassword.getString("id");
        String password = idAndPassword.getString("password");

        boolean flag = userService.selectByIdAndPassword(Integer.parseInt(id), password)!=null;
        return flag?new Result(200,"登录成功"):new Result(201,"登录失败");
    }

    //修改密码
    @PostMapping("/change")
    @ResponseBody
    public Result changePassword(@RequestBody JSONObject data){
        String aNew = data.getString("newPw");
        String aAld = data.getString("oldPw");
        String id = data.getString("id");

        Boolean flag = userService.changePassword(aAld, aNew, Integer.parseInt(id));
        return flag?new Result(200,"修改成功"):new Result(201,"修改失败");
    }







}
