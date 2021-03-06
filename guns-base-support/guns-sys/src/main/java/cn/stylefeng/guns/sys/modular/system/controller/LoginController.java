/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.sys.modular.system.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.auth.service.AuthService;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.tenant.context.DataBaseNameHolder;
import cn.stylefeng.guns.base.tenant.context.TenantCodeHolder;
import cn.stylefeng.guns.base.tenant.entity.TenantInfo;
import cn.stylefeng.guns.base.tenant.service.TenantInfoService;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.sys.core.constant.DefaultAvatar;
import cn.stylefeng.guns.sys.core.exception.InvalidKaptchaException;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.entity.Role;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.RoleService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.mutidatasource.DataSourceContextHolder;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SessionManager sessionManager;

    /**
     * 跳转到主页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model,HttpServletRequest request) {

        //判断用户是否登录
        if (LoginContextHolder.getContext().hasLogin()) {
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();

            //用户信息为空，提示账号没分配角色登录不进去
            if (userIndexInfo == null) {
                model.addAttribute("tips", "该用户没有角色，无法登陆");
                model.addAttribute("ifVideo","no");
                return "/webIndex.html";
            } else {
                model.addAllAttributes(userIndexInfo);
                String url = DefaultAvatar.getLoginUrl();
                LoginUser user = LoginContextHolder.getContext().getUser();
                User user1 = userService.getById(user.getId());
                List roles = user.getRoleList();
                long unit = 3;
                if (user1.getName() != "" && user1.getName() != null){
                    model.addAttribute("userName", user1.getName());
                }else {
                    model.addAttribute("userName", user1.getAccount());
                }
                if (user1.getWechatId() != null && !user1.getWechatId().equals("")){
                    model.addAttribute("wechatSign", "yes");
                }else {
                    model.addAttribute("wechatSign", "no");
                }
                for (int i = 0;i < roles.size();i++){
                    Role role = roleService.getById(Long.parseLong(roles.get(i).toString()));
                    if (role.getIfAdmin() == 1){
                        model.addAttribute("ifAdmin", 1);
                    }
                }
                model.addAttribute("roleNames", user.getRoleNames());
                //String loginUrl = "redirect:http://cesf.nies.net.cn/pub/lt_new_6/";
                String loginUrl = "/webIndex.html";
                if (roles.contains(unit)){
                    if (url.equals("/greatResult/add")){
                        loginUrl = "/unitResult.html";
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/holdForum/add")){
                        loginUrl = "/unitForum.html";
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/socialForum/add")){
                        loginUrl = "/social.html";
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/collectTopic/add")){
                        loginUrl = "/collect.html";
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/reviewMajor/add")){
                        loginUrl = "/unitReport.html";
                        DefaultAvatar.setLoginUrl("");
                    }
                }else {
                    if (url.equals("/greatResult/add")){
                        loginUrl = "/result.html";
                        model.addAttribute("menuUrl","greatResult");
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/holdForum/add")){
                        loginUrl = "/forum.html";
                        model.addAttribute("menuUrl","holdForum");
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/socialForum/add")){
                        loginUrl = "/social.html";
                        model.addAttribute("menuUrl","socialForum");
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/collectTopic/add")){
                        loginUrl = "/collect.html";
                        model.addAttribute("menuUrl","collectTopic");
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/thesis")){
                        model.addAttribute("userName", user.getName());
                        model.addAttribute("menuUrl","thesis");
                        model.addAttribute("isReview", "yes");

                        if(roles.contains(1)){
                            loginUrl = "/thesis/thesis_disable.html";
                        } else if(roles.contains(4)){
                            loginUrl = "/thesis/thesis_review.html";

                        } else{
                            loginUrl = "没有权限";
                        }
                        DefaultAvatar.setLoginUrl("");
                    }
                    if (url.equals("/meetMember/add")) {
                        User userMeet = userService.getById(user.getId());
                        String userTitle = userMeet.getTitle();
                        if(userTitle != null && userTitle != ""){
                            request.setAttribute("userTitle",userTitle);
                        }else{
                            request.setAttribute("userTitle","无职称");
                        }
                        loginUrl = "/meet_reg.html";
                        model.addAttribute("menuUrl","meetMember");
                        DefaultAvatar.setLoginUrl("");
                    }
                }
                model.addAttribute("ifVideo","no");
                //model.addAttribute("loginUrl", loginUrl);
                return loginUrl;
            }

        } else {
            //model.addAttribute("tips", "请登陆！");
            model.addAttribute("ifVideo","yes");
            return "/webIndex.html";
        }
    }

    /**
     * 跳转到主页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model,HttpServletRequest request) {

        //判断用户是否登录
        if (LoginContextHolder.getContext().hasLogin()) {
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();

            //用户信息为空，提示账号没分配角色登录不进去
            if (userIndexInfo == null) {
                model.addAttribute("tips", "该用户没有角色，无法登陆");
                return "/login.html";
            } else {
                model.addAllAttributes(userIndexInfo);
                LoginUser user = LoginContextHolder.getContext().getUser();
                if (user != null){
                    if (user.getName() != "" && user.getName() != null){
                        model.addAttribute("userName", user.getName());
                    }else {
                        model.addAttribute("userName", user.getAccount());
                    }
                    model.addAttribute("roleNames",user.getRoleNames());
                }
                List roles = user.getRoleList();
                int ifAdmin = 0;
                for (int i = 0;i < roles.size();i++){
                    Role role = roleService.getById(Long.parseLong(roles.get(i).toString()));
                    if (role.getIfAdmin() == 1){
                        model.addAttribute("ifAdmin", 1);
                        ifAdmin = 1;
                    }
                }
                if (ifAdmin == 1){
                    return "/index.html";
                }else {
                    model.addAttribute("ifVideo","no");
                    return "/webIndex.html";
                }

            }

        } else {
            model.addAttribute("ifVideo","no");
            return "/webIndex.html";
        }
    }

    /**
     * 跳转到登录页面
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        if (LoginContextHolder.getContext().hasLogin()) {
            model.addAttribute("loginUrl","/webIndex.html");
            model.addAttribute("ifVideo","no");
            return "/webIndex.html";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData loginVali(HttpServletRequest request, HttpServletResponse response) {

        String username = super.getPara("username");
        String password = super.getPara("password");

        if (ToolUtil.isOneEmpty(username, password)) {
            throw new RequestEmptyException("账号或密码为空！");
        }

        //如果系统开启了多租户开关，则添加租户的临时缓存
        if (ConstantsContext.getTenantOpen()) {
            String tenantCode = super.getPara("tenantCode");
            if (ToolUtil.isNotEmpty(tenantCode)) {

                //从spring容器中获取service，如果没开多租户功能，没引入相关包，这里会报错
                TenantInfoService tenantInfoService = null;
                try {
                    tenantInfoService = SpringContextHolder.getBean(TenantInfoService.class);
                } catch (Exception e) {
                    throw new ServiceException(500, "找不到多租户service，请检查是否引入guns-tenant模块！");
                }

                //获取租户信息
                TenantInfo tenantInfo = tenantInfoService.getByCode(tenantCode);
                if (tenantInfo != null) {
                    String dbName = tenantInfo.getDbName();

                    //添加临时变量（注意销毁）
                    TenantCodeHolder.put(tenantCode);
                    DataBaseNameHolder.put(dbName);
                    DataSourceContextHolder.setDataSourceType(dbName);
                } else {
                    throw new ServiceException(BizExceptionEnum.NO_TENANT_ERROR);
                }
            }
        }

        //验证验证码是否正确
        if (ConstantsContext.getKaptchaOpen()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        //登录并创建token
        String token = authService.login(username, password, request);

        return new SuccessResponseData(token);
    }

    /**
     * 利用已有的token进行登录（一般用在oauth登录）
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/sysTokenLogin")
    public String sysTokenLogin(@RequestParam(value = "token", required = false) String token, Model model) {
        if (ToolUtil.isNotEmpty(token)) {

            //从session获取用户信息，没有就是token无效
            LoginUser user = sessionManager.getSession(token);
            if (user == null) {
                return "/login.html";
            }

            //创建当前登录上下文
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //渲染首页需要的用户信息
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();
            if (userIndexInfo == null) {
                model.addAttribute("tips", "该用户没有角色，无法登陆！");
                return "/login.html";
            } else {
                model.addAllAttributes(userIndexInfo);
            }

            //创建cookie
            authService.addLoginCookie(token);

            return "/index.html";
        } else {
            model.addAttribute("tips", "token请求为空，无法登录！");
            return "/login.html";
        }
    }

    /**
     * 退出登录
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public ResponseData logOut() {
        authService.logout();
        return new SuccessResponseData();
    }

    /**
     * 主页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        if (LoginContextHolder.getContext().hasLogin()){
            LoginUser user = LoginContextHolder.getContext().getUser();
            User user1 = this.userService.getById(user.getId());
            if (user1 != null){
                if (user1.getName() != "" && user1.getName() != null){
                    model.addAttribute("userName", user1.getName());
                }else {
                    model.addAttribute("userName", user1.getAccount());
                }
                model.addAttribute("roleNames",user.getRoleNames());
                List roles = user.getRoleList();
                for (int i = 0;i < roles.size();i++){
                    Role role = roleService.getById(Long.parseLong(roles.get(i).toString()));
                    if (role.getIfAdmin() == 1){
                        model.addAttribute("ifAdmin", 1);
                    }
                }
            }

        }
        model.addAttribute("ifVideo","no");
        return "/webIndex.html";
    }

    /**
     * 主页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/chatLogout")
    public String chatLogout(Model model) {
        authService.logout();
        ResponseData data = new SuccessResponseData();
        if (data.getMessage().equals("请求成功")) {
            return "/logout.html";
        }
        model.addAttribute("ifVideo","no");
        return "/webIndex.html";
    }
}