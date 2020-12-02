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

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.auth.service.AuthService;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.constant.Const;
import cn.stylefeng.guns.sys.core.constant.dictmap.UserDict;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.core.constant.state.ManagerStatus;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.core.util.DefaultImages;
import cn.stylefeng.guns.sys.core.util.SaltUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.model.params.UserPosParam;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.sys.modular.system.warpper.UserWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
@Validated
@Slf4j
public class UserMgrController extends BaseController {

    private static String PREFIX = "/modular/system/user/";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到个人中心
     *
     * @author CHU
     * @Date 2020/09/02
     */
    @RequestMapping("toPersonCenter")
    public String toPersonCenter(Model model) {
        Long userId = LoginContextHolder.getContext().getUserId();
        User user = this.userService.getById(userId);

        model.addAllAttributes(BeanUtil.beanToMap(user));
        model.addAttribute("avatar", DefaultImages.defaultAvatarUrl());
        model.addAttribute("menuUrl", "toPersonCenter");
        if (user.getRoleId().indexOf("4") > -1){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        LogObjectHolder.me().set(user);
        return "/modular/frame/person_center.html";
    }

    @RequestMapping("/userPhoneLogin")
    public String userPhoneLogin() {
        return "/modular/frame/user_phone_login.html";
    }

    @RequestMapping("/phoneLogin")
    @ResponseBody
    public ResponseData phoneLogin(HttpServletRequest request,String phone, String smsCode) {
        JSONObject json = (JSONObject)request.getSession().getAttribute("smsCode");
        ResponseData responseData = new ResponseData();
        if(json == null){
            responseData.setMessage("codeError");
            return responseData;
        }
        if(!json.getString("smsCode").equals(smsCode)){
            responseData.setMessage("codeError");
            return responseData;
        }
        if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5){
            responseData.setMessage("overTime");
            return responseData;
        }
        User user = userService.getByPhone(phone);
        if (user != null){
            //登录并创建token
            String token = authService.login(user.getAccount(),request);
            new SuccessResponseData(token);
            responseData.setMessage("success");
        }else {
            responseData.setMessage("error");
        }
        return responseData;
    }

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "user_addAdmin.html";
    }
    @RequestMapping("/user_addUnit")
    public String addUnitView() {
        return PREFIX + "user_addUnitAdmin.html";
    }

    /**
     * 跳转到注册的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_registe")
    public String registeView() {
        return PREFIX + "user_add.html";
    }
    @RequestMapping("/user_registeUnit")
    public String registeUnitView() {
        return PREFIX + "user_addUnit.html";
    }
    @RequestMapping("/registerSuccess")
    public String registerSuccess() {
        return PREFIX + "user_registerSuccess.html";
    }

    /**
     * 跳转到生成账号的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_addAccount")
    public String toAddAccount() {
        return PREFIX + "user_addAccount.html";
    }


    /**
     * 跳转到忘记密码的页面
     *
     * @author chu
     * @Date 2020/06/30 22:43
     */
    @RequestMapping("/forgetPwd")
    public String forgetPwd() {
        return PREFIX + "forgetPwd.html";
    }
    @RequestMapping("/toForgetPwdOne")
    public String toForgetPwdOne(ModelMap map, String account) {
        User user = this.userService.getByAccount(account);
        User userPhone = this.userService.getByPhone(account);
        if (user != null){
            map.addAttribute("account", account);
            map.addAttribute("phone", user.getPhone());
        }else if (userPhone != null){
            map.addAttribute("account", userPhone.getAccount());
            map.addAttribute("phone", userPhone.getPhone());
        }
        return PREFIX + "phonePwd.html";

    }
    @RequestMapping("/toNewPwd")
    public String toNewPwd(ModelMap map, String account) {
        map.addAttribute("account", account);
        return PREFIX + "newPwd.html";
    }
    @RequestMapping("/toForgetPwdTwo")
    public String toForgetPwdTwo() {
        return PREFIX + "finishPwd.html";
    }
    @RequestMapping("/phonePwd")
    @ResponseBody
    public ResponseData phonePwd(HttpServletRequest request, String account, String phone, String smsCode) {
        JSONObject json = (JSONObject)request.getSession().getAttribute("smsCode");
        ResponseData responseData = new ResponseData();
        User user = this.userService.getByAccount(account);
        if (json == null){
            responseData.setMessage("codeError");
            return responseData;
        }
        if (!json.getString("phone").equals(phone)){
            responseData.setMessage("phoneError");
            return responseData;
        }
        if (!json.getString("phone").equals(user.getPhone())){
            responseData.setMessage("userPhoneError");
            return responseData;
        }
        if (!json.getString("smsCode").equals(smsCode)){
            responseData.setMessage("codeError");
            return responseData;
        }
        if ((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5){
            responseData.setMessage("overTime");
            return responseData;
        }
        responseData.setMessage(account);
        return responseData;
    }
    @RequestMapping("/forgetPwdOne")
    @ResponseBody
    public ResponseData forgetPwdOne(HttpServletRequest request, String account, String phone, String smsCode) {
        JSONObject json = (JSONObject)request.getSession().getAttribute("smsCode");
        ResponseData responseData = new ResponseData();
        User user = this.userService.getByAccount(account);
        User userPhone = this.userService.getByPhone(account);
        if (user == null && userPhone == null){
            responseData.setMessage("userError");
            return responseData;
        }
        responseData.setMessage(account);
        return responseData;
    }
    @RequestMapping("/forgetPwdTwo")
    @ResponseBody
    public ResponseData forgetPwdTwo(String password, String account) {
        ResponseData responseData = new ResponseData();
        User user = this.userService.getByAccount(account);
        if (user == null){
            responseData.setMessage("error");
            return responseData;
        }
        user.setSalt(SaltUtil.getRandomSalt());
        user.setPassword(SaltUtil.md5Encrypt(password, user.getSalt()));
        if (this.userService.updateById(user)){
            responseData.setMessage(account);
            return responseData;
        }else {
            responseData.setMessage("error");
            return responseData;
        }
    }

    /**
     * 跳转到角色分配页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/role_assign")
    public String roleAssign(@RequestParam Long userId, Model model) {
        model.addAttribute("userId", userId);
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/user_edit")
    public String userEdit(@RequestParam Long userId) {
        User user = this.userService.getById(userId);
        LogObjectHolder.me().set(user);
        String[] roles = user.getRoleId().split(",");
        for (String role : roles){
            if (role.equals("5")){
                return PREFIX + "guest_edit.html";
            }
        }
        return PREFIX + "user_edit.html";
    }

    /**
     * 获取用户详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public SuccessResponseData getUserInfo(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException();
        }

        this.userService.assertAuth(userId);
        return new SuccessResponseData(userService.getUserInfo(userId));
    }


    /**
     * 修改当前用户的密码
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam("oldPassword") @NotBlank String oldPassword,
                            @RequestParam("newPassword") @Length(min = 6, max = 12) String newPassword) {
        this.userService.changePwd(oldPassword, newPassword);
        return SUCCESS_TIP;
    }
    @RequestMapping("/changePhone")
    @ResponseBody
    public ResponseData changePhone(HttpServletRequest request, String phone, String smsCode) {
        JSONObject json = (JSONObject)request.getSession().getAttribute("smsCode");
        ResponseData responseData = new ResponseData();
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        User user = userService.getById(loginUser.getId());
        User userPhone = userService.getByPhone(phone);
        UserDto userDto = new UserDto();
        if (json == null){
            responseData.setMessage("codeError");
            return responseData;
        }
        if (!json.getString("phone").equals(phone)){
            responseData.setMessage("phoneError");
            return responseData;
        }
        if (userPhone != null){
            responseData.setMessage("userError");
            return responseData;
        }
        if (!json.getString("smsCode").equals(smsCode)){
            responseData.setMessage("codeError");
            return responseData;
        }
        if ((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5){
            responseData.setMessage("overTime");
            return responseData;
        }
        userDto.setUserId(user.getUserId());
        userDto.setPhone(phone);
        userService.editUser(userDto);
        responseData.setMessage("success");
        return responseData;
    }
    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String timeLimit,
                       @RequestParam(required = false) Long deptId) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        if (LoginContextHolder.getContext().isAdmin()) {
            Page<Map<String, Object>> users = userService.selectUsers(null, name, beginTime, endTime, deptId);
            Page wrapped = new UserWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        } else {
            DataScope dataScope = new DataScope(LoginContextHolder.getContext().getDeptDataScope());
            Page<Map<String, Object>> users = userService.selectUsers(dataScope, name, beginTime, endTime, deptId);
            Page wrapped = new UserWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }
    }

    /**
     * 管理员添加用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/addAdmin")
    //@BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    public ResponseData addAdmin(@Valid UserDto user) {
        this.userService.addUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 添加管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/add")
    //@BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    public ResponseData add(@Valid UserDto user, HttpServletRequest request, String smsCode) {
        JSONObject json = (JSONObject)request.getSession().getAttribute("smsCode");
        ResponseData responseData = new ResponseData();
        if(json == null){
            responseData.setMessage("codeError");
            return responseData;
        }
        if(!json.getString("phone").equals(user.getPhone())){
            responseData.setMessage("phoneError");
            return responseData;
        }
        if(!json.getString("smsCode").equals(smsCode)){
            responseData.setMessage("codeError");
            return responseData;
        }
        if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5){
            responseData.setMessage("overTime");
            return responseData;
        }
        this.userService.addUser(user);
        return SUCCESS_TIP;
    }

    @RequestMapping("/addCheck")
    @ResponseBody
    public ResponseData addCheck(@Valid UserDto user, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        // 判断账号是否重复
        User theUser = userService.getByAccount(user.getAccount());
        if (theUser != null) {
            responseData.setMessage("existAccount");
            return responseData;
        }
        User pUser = userService.getByPhone(user.getPhone());
        if (pUser != null) {
            responseData.setMessage("existPhone");
            return responseData;
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    public ResponseData edit(UserDto user) {
        // 判断手机号是否重复
        ResponseData responseData = new ResponseData();
        User pUser = this.userService.getByPhone(user.getPhone());
        if (pUser != null) {
            if (!user.getAccount().equals(pUser.getAccount())){
                responseData.setMessage("phoneError");
                return responseData;
            }else {
            }
        }

        this.userService.editUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 删除管理员（逻辑删除）
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     *//*
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.deleteUser(userId);
        return SUCCESS_TIP;
    }*/

    /**
     * 查看管理员详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.assertAuth(userId);
        return this.userService.getById(userId);
    }

    /**
     * 重置管理员的密码
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData reset(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.assertAuth(userId);
        User user = this.userService.getById(userId);
        user.setSalt(SaltUtil.getRandomSalt());
        user.setPassword(SaltUtil.md5Encrypt(ConstantsContext.getDefaultPassword(), user.getSalt()));
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData freeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        this.userService.assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.assertAuth(userId);
        this.userService.editUserByWrong(userId, ManagerStatus.OK.getCode(), 0);
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData setRole(@RequestParam("userId") Long userId,
                                @RequestParam("roleIds") @NotBlank String roleIds) {
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        this.userService.assertAuth(userId);
        this.userService.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * 上传图片
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {

        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        try {
            String fileSavePath = ConstantsContext.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * 选择办理人
     *
     * @author fengshuonan
     * @Date 2019-8-22 15:48
     */
    @RequestMapping("/listUserAndRoleExpectAdmin")
    @ResponseBody
    public LayuiPageInfo listUserAndRoleExpectAdmin() {
        Page pageContext = LayuiPageFactory.defaultPage();
        IPage page = userService.listUserAndRoleExpectAdmin(pageContext);
        return LayuiPageFactory.createPageInfo(page);
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData selectUser(HttpServletRequest request){
        String idStr = request.getParameter("userId");
        long userId = Long.parseLong(idStr);
        User user = userService.getById(userId);
        return ResponseData.success(user);
    }
}
