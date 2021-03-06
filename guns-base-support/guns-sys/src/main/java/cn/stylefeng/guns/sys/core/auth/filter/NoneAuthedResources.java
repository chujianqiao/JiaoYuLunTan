package cn.stylefeng.guns.sys.core.auth.filter;

/**
 * 不需要身份验证的资源
 *
 * @author fengshuonan
 * @Date 2020/3/1 16:19
 */
public class NoneAuthedResources {

    /**
     * 前端接口资源
     */
    public static final String[] FRONTEND_RESOURCES = {
            "/assets/**",
            "/favicon.ico",
            "/activiti-editor/**"
    };


    /**
     * 不要权限校验的后端接口资源
     * <p>
     * ANT风格的接口正则表达式：
     * <p>
     * ? 匹配任何单字符<br/>
     * * 匹配0或者任意数量的 字符<br/>
     * ** 匹配0或者更多的 目录<br/>
     */
    public static final String[] BACKEND_RESOURCES = {

            //主页
            "/",
            "/index",
            "/translation/languages",
            "/translation/changeUserTranslation",

            //注册
            "/mgr/add",
            "/mgr/addCheck",
            "/mgr/user_registe",
            "/mgr/user_registeUnit",
            "/check/checkSMS",
            "/check/checkEmail",
            "/mgr/forgetPwd",
            "/mgr/toForgetPwdOne",
            "/mgr/toForgetPwdTwo",
            "/mgr/forgetPwdOne",
            "/mgr/forgetPwdTwo",
            "/mgr/forgetPwdOnePhone",
            "/mgr/toNewPwd",
            "/mgr/phonePwd",
            "/mgr/registerSuccess",
            "/mgr/userPhoneLogin",
            "/mgr/phoneLogin",

            //加载当前语言字典并缓存
            "/translation/getUserTranslation",

            // 锁屏
            "/system/lock",

            //获取验证码
            "/kaptcha",

            //rest方式获取token入口
            "/rest/login",

            //oauth登录的接口
            "/oauth/render/*",
            "/oauth/callback/*",

            //单点登录接口
            "/ssoLogin",
            "/sysTokenLogin",

            // 登录接口放开过滤
            "/login",

            //微信Token
            "/check/checkToken",
            "/weiXin/*",

            // session登录失效之后的跳转
            "/global/sessionError",

            // 图片预览 头像
            "/system/preview/*",

            // 错误页面的接口
            "/error",
            "/global/error",

            // 测试多数据源的接口，可以去掉
            "/tran/**",

            //获取租户列表的接口
            "/tenantInfo/listTenants",

            //自定义微信菜单
            "/weiXinMenu/createMenu"

    };

}
