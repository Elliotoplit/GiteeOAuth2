# GiteeOAuth2
OAuth2.0 登录验证Gitee和Wechat示例

注意：
- Gitee 可以设置内网地址，对于初学者来说更方便
- 微信的授权登录需要公网ip，无法设置内网地址
  - 解决方法1：cpolar内网穿透得到一个公网ip
  - 解决方法2：购买服务器

本项目实现了OAuth的后台请求处理功能
-  获取token
-  刷新token
-  获取用户数据


本项目code由前端获取，返回给后端gitee/auth接口

**更多细节请参见Gitee官方** <br>

> Gitee创建第三方应用<br>
> https://gitee.com/oauth/applications <br>


> Gitee OAuth2.0 接口文档 <br>
> https://gitee.com/api/v5/swagger#/ <br>

> OAuth教程文档<br>
> https://gitee.com/api/v5/oauth_doc#/
