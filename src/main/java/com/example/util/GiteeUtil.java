package com.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.domain.GiteeToken;
import com.example.domain.UpdateGiteeUser;
import com.example.domain.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GiteeUtil {

    public static final String ClientID = "8e3e44e237892aef9c1bfd1d3b2f5395b45483433707fc1391028975a1e736ee";
    public static final String ClientSecret = "88efc2c187e46a92ad2f78165c766152590ccd14df2847472acf616e21ede8cd";
    //    public static final String RedirectUri = "http://47.120.8.115/me"; //回调地址
//    public static final String RedirectUri = "http://localhost:5173/login"; //回调地址
    public static final String RedirectUri = "http://47.120.8.115/login"; //回调地址
    public static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * 拼接 URL
     * @return url
     */
    public static String getUrl() {
        String url = URLEncoder.encode(RedirectUri, StandardCharsets.UTF_8);
        String s = "https://gitee.com/oauth/authorize?client_id=" + ClientID + "&redirect_uri=" + url + "&response_type=code";
        System.out.println("s = " + s);
        return s;
    }

    /**
     * 发起请求获取Token
     * @param code 校验码
     * @return 返回数据
     * @throws Exception exception
     */
    public static GiteeToken getToken(String code) throws Exception {
        //新建httpClient对象 新建post请求对象
        HttpPost postRequest = new HttpPost("https://gitee.com/oauth/token");

        //post请求对象传入值
        StringEntity input = new StringEntity(
                        "grant_type=authorization_code&" +
                        "code=" + code +
                        "&client_id=" + ClientID +
                        "&redirect_uri=" + RedirectUri +
                        "&client_secret=" + ClientSecret);
        input.setContentType("application/x-www-form-urlencoded");
        postRequest.setEntity(input);

        //httpClient执行 post请求 并获取返回内容·
        HttpResponse response = httpClient.execute(postRequest);
        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity);
        System.out.println("JSONSTR: "+jsonStr);
        //控制台

        return JSON.parseObject(jsonStr,GiteeToken.class);
    }

    /**
     * 刷新 Gitee 的 token
     * @param refreshToken 先前获取到的refreshToken
     * @return 返回新的 GiteeToken 对象
     * @throws IOException exception
     */
    public static GiteeToken RefreshToken(String refreshToken) throws IOException {
        HttpPost postRequest = new HttpPost("https://gitee.com/oauth/token");

        StringEntity input = new StringEntity("grant_type=refresh_token&refresh_token=" + refreshToken);
        input.setContentType("application/x-www-form-urlencoded");

        //数据传入方法体
        postRequest.setEntity(input);

        //执行请求
        HttpResponse response = httpClient.execute(postRequest);
        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity);

        System.out.println(jsonStr);

        //将JSON数据实例化为GiteeToken对象
        return JSON.parseObject(jsonStr, GiteeToken.class);
    }

    /**
     * 根据用户的token获取用户的信息
     *
     * @param token access_token
     * @return W
     * @throws IOException exception
     */
    public static User getInfo(String token) throws IOException {
        //配置
        HttpGet emailGet = new HttpGet("https://gitee.com/api/v5/emails?access_token=" + token);
        HttpGet userGet = new HttpGet("https://gitee.com/api/v5/user?access_token=" + token);

        //执行请求获取内容
        HttpEntity entity1 = httpClient.execute(emailGet).getEntity();
        HttpEntity entity2 = httpClient.execute(userGet).getEntity();
        //获取json字符串
        String jsonStr4email = EntityUtils.toString(entity1);
        String jsonStr4User = EntityUtils.toString(entity2);
        //转为返回对象
        User user = JSON.parseObject(jsonStr4User, User.class);
        //获取GiteeID
        Integer giteeId = JSONObject.parseObject(jsonStr4User).getInteger("id");
        user.setGiteeId(giteeId);
        //获取GiteeName
        String giteeName = JSONObject.parseObject(jsonStr4User).getString("name");
        user.setGiteeName(giteeName);
        //判断邮箱字符串，如果邮箱存在则加入返回对象，否则保留null
        if (!jsonStr4email.equals("[]")) {
            String substring = jsonStr4email.substring(1, jsonStr4email.length() - 1);//修建json
            String email = JSON.parseObject(substring).getString("email");//获取邮箱
            user.setEmail(email);//将邮箱信息添加至用户
        }
        return user;
    }

    //修改Gitee数据：email
    public static Boolean updateGitte(UpdateGiteeUser user) throws IOException {
        HttpPatch httpPatch = new HttpPatch("https://gitee.com/api/v5/user");
        httpPatch.setHeader("Content-type", "application/json");
        Object jsonParam = JSON.toJSON(user);
        StringEntity entity = new StringEntity(jsonParam.toString(), StandardCharsets.UTF_8);
        httpPatch.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPatch);
        System.out.println("xxx:"+EntityUtils.toString(response.getEntity()));
        return response.getStatusLine().getStatusCode()==200;
    }
}
