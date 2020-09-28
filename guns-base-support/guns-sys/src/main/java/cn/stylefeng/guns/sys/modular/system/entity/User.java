package cn.stylefeng.guns.sys.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-12
 */
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      @TableId(value = "user_id", type = IdType.ID_WORKER)
    private Long userId;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * md5密码盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 性别(字典)
     */
    @TableField("sex")
    private String sex;

    /**
     * 电子邮件
     */
    @TableField("email")
    private String email;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 角色id(多个逗号隔开)
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 部门id(多个逗号隔开)
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 状态(字典)
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
      @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
      @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
      @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 乐观锁
     */
    @TableField("version")
    private Integer version;

    /**
     * 是否为会员  0-非会员   1-会员
     */
    @TableField("vip")
    private Integer vip;

    /**
     * 工作单位
     */
    @TableField("work_unit")
    private String workUnit;

    /**
     * 职称
     */
    @TableField("title")
    private String title;

    /**
     * 职务
     */
    @TableField("post")
    private String post;

    /**
     * 学历
     */
    @TableField("education")
    private String education;

    /**
     * 研究方向
     */
    @TableField("direction")
    private String direction;

    /**
     * 错误次数
     */
    @TableField("wrong_times")
    private Integer wrongTimes;

    /**
     * 发言稿能否被下载
     */
    @TableField("can_download_word")
    private Integer canDownloadWord;

    /**
     * ppt能否被下载
     */
    @TableField("can_download_ppt")
    private Integer canDownloadPpt;

    /**
     * 发言稿路径
     */
    @TableField("word_path")
    private String wordPath;

    /**
     * 发言稿名称
     */
    @TableField("word_name")
    private String wordName;

    /**
     * ppt路径
     */
    @TableField("ppt_path")
    private String pptPath;

    /**
     * ppt名称
     */
    @TableField("ppt_name")
    private String pptName;
    /**
     * 嘉宾简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 微信昵称
     */
    @TableField("wechat_name")
    private String wechatName;

    /**
     * 微信openid
     */
    @TableField("wechat_id")
    private String wechatId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getWrongTimes() {
        return wrongTimes;
    }

    public void setWrongTimes(Integer wrongTimes) {
        this.wrongTimes = wrongTimes;
    }

    public Integer getCanDownloadWord() {
        return canDownloadWord;
    }

    public void setCanDownloadWord(Integer canDownloadWord) {
        this.canDownloadWord = canDownloadWord;
    }

    public Integer getCanDownloadPpt() {
        return canDownloadPpt;
    }

    public void setCanDownloadPpt(Integer canDownloadPpt) {
        this.canDownloadPpt = canDownloadPpt;
    }

    public String getWordPath() {
        return wordPath;
    }

    public void setWordPath(String wordPath) {
        this.wordPath = wordPath;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getPptPath() {
        return pptPath;
    }

    public void setPptPath(String pptPath) {
        this.pptPath = pptPath;
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    @Override
    public String toString() {
        return "User{" +
        "userId=" + userId +
        ", avatar=" + avatar +
        ", account=" + account +
        ", password=" + password +
        ", salt=" + salt +
        ", name=" + name +
        ", birthday=" + birthday +
        ", sex=" + sex +
        ", email=" + email +
        ", phone=" + phone +
        ", roleId=" + roleId +
        ", deptId=" + deptId +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        ", version=" + version +
        ", vip=" + vip +
        ", workUnit=" + workUnit +
        ", title=" + title +
        ", post=" + post +
        ", education=" + education +
        ", direction=" + direction +
        ", wrongTimes=" + wrongTimes +
        ", canDownloadWord=" + canDownloadWord +
        ", canDownloadPpt=" + canDownloadPpt +
        ", wordPath=" + wordPath +
        ", wordName=" + wordName +
        ", pptPath=" + pptPath +
        ", pptName=" + pptName +
        ", introduction=" + introduction +
        ", wechatName=" + wechatName +
        ", wechatId=" + wechatId +
        "}";
    }
}
