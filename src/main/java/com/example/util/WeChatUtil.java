package com.example.util;

import com.alibaba.fastjson.JSON;
import com.example.domain.WeChatToken;
import com.example.domain.WeChatUser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeChatUtil {
    //常量区
    public static final String AppId = "wx57818914d6f4c39c";
    public static final String AppSecret = "51026d651e050f7985de0d022eee5b11";
    public static final String RedirectUri = "https://10.131.140.99/wechat/auth";


    //http请求客户端
    static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    //方法区
    /**
     * Get encoded WeChat Authorization Url which embedded AppID and so on
     * @return completed url
     */
    public static String getUrl(){
        //url要转为 UrlEncode编码格式
        String CodedRedirectUri = URLEncoder.encode(RedirectUri, StandardCharsets.UTF_8);
        return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid="+AppId+
                "&redirect_uri="+CodedRedirectUri+
                "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect&forcePopup=true";
    }

    /**
     * Get token object by code
     * @param code 授权码
     * @return 返回 WeChatToken 对象
     * @throws IOException IO异常
     */
    public static WeChatToken getToken(String code) throws IOException {
        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"+
                "appid="+AppId+
                "&secret="+AppSecret+
                "&code="+code+
                "&grant_type=authorization_code";
        //get请求对象装入url
        HttpGet httpGet = new HttpGet(tokenUrl);
        String responseResult = "";
        //发起请求 如果请求成功 则接收返回的数据转为UTF-8格式
        HttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode()==200) {
            responseResult = EntityUtils.toString(response.getEntity(),"UTF-8");
        }
        //将结果封装到WechatToken对象 并返回
        return JSON.parseObject(responseResult, WeChatToken.class);
    }

    /**
     * Refresh token
     * @param token:not accessToken,this is refresh token
     * @return new Token Object
     * @throws IOException IO异常
     */
    public static WeChatToken refreshToken(String token) throws IOException {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
                "appid="+AppId+
                "&grant_type=refresh_token" +
                "&refresh_token="+token;
        HttpGet httpGet =new HttpGet(url);
        //执行刷新
        HttpResponse response = httpClient.execute(httpGet);
        String jsonStr = "";
        if (response.getStatusLine().getStatusCode()==200) {
            jsonStr = EntityUtils.toString(response.getEntity());
        }
        return JSON.parseObject(jsonStr, WeChatToken.class);
    }

    /**
     * Get user information by access_token and openID
     * @param accessToken 用户访问token
     * @param openId 用户唯一id
     * @return 微信用户对象
     * @throws IOException IO异常
     */
    public static WeChatUser getInfo(String accessToken,String openId) throws IOException{
        String url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token="+accessToken+
                "&openid=" +openId+
                "&lang=zh_CN";
        HttpGet httpGet = new HttpGet(url);
        String jsonStr = "";
        //执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode()==200) {
            jsonStr = EntityUtils.toString(response.getEntity());
        }
        System.out.println("jsonStr = " + jsonStr);
        return JSON.parseObject(jsonStr,WeChatUser.class);
    }
}
