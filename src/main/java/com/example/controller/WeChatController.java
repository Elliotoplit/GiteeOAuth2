package com.example.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.example.domain.Result;
import com.example.domain.WeChatToken;
import com.example.domain.WeChatUser;
import com.example.util.WeChatUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/wechat")
@CrossOrigin
public class WeChatController {

    /**
     * check the link status to WeChat Public Platform
     * @param signature signature
     * @param timeStamp timeStamp
     * @param nonce nonce
     * @param echoStr echoStr
     * @return the same echoStr
     */
    @GetMapping("/check")
    public String WXCheck(@RequestParam("signature") String signature,
                          @RequestParam("timestamp") String timeStamp,
                          @RequestParam("nonce") String nonce,
                          @RequestParam("echostr") String echoStr){
        return echoStr;
    }

    /**
     * Generate QRCode jumped to WeChat Authorization website
     * @param response 返回图片流
     * @throws IOException IO异常
     */
    @GetMapping("/login")
    public void wxLogin(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        QrCodeUtil.generate(WeChatUtil.getUrl(),400,400,"jpg",response.getOutputStream());
    }

    /**
     * Authorize code to get token
     * @param code code
     * @param state state
     * @param request request
     * @param response response
     * @param session session
     * @return result
     * @throws IOException io
     */
    @GetMapping("/auth")
    public Result callBack(String code, String state, HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) throws IOException {
        WeChatToken weChatToken = WeChatUtil.getToken(code);
        System.out.println("token = " + weChatToken.getAccessToken() +"\n" +"getOpenId = " + weChatToken.getOpenid());

        return weChatToken.getErrCode() == 40029?
                new Result(201, weChatToken.getErrMsg()):
                new Result(200, weChatToken);
    }

    /**
     * Refresh token
     * @param refreshToken refreshToken
     * @return new token
     * @throws IOException io
     */
    @GetMapping("/refresh")
    public Result refresh(String refreshToken) throws IOException {
        WeChatToken weChatToken = WeChatUtil.refreshToken(refreshToken);

        return weChatToken.getErrCode()==40029?
                new Result(201,"获取失败"):
                new Result(200, weChatToken);
    }

    /**
     * Get user information
     * @param token token
     * @param openId openID
     * @return user info
     * @throws IOException io
     */
    @GetMapping("/info")
    public Result getInfo(String token,String openId,HttpServletResponse response) throws IOException {
        WeChatUser user = WeChatUtil.getInfo(token, openId);
        System.out.println("user = " + user);

        return user.getErrCode()==40003?
                new Result(201,"获取失败"):
                new Result(200,user);
    }

    
}
