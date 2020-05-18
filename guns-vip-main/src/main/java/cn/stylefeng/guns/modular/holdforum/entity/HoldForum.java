package cn.stylefeng.guns.modular.holdforum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 承办方论坛表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-14
 */
@TableName("hold_forum")
public class HoldForum implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 论坛ID
     */
    @TableId(value = "forum_id", type = IdType.ID_WORKER)
    private Long forumId;

    /**
     * 论坛名称
     */
    @TableField("forum_name")
    private String forumName;

    /**
     * 论坛描述
     */
    @TableField("forum_desc")
    private String forumDesc;

    /**
     * 申报状态; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
     */
    @TableField("apply_status")
    private Integer applyStatus;

    /**
     * 单位名称
     */
    @TableField("unit_name")
    private String unitName;

    /**
     * 单位简介
     */
    @TableField("unit_desc")
    private String unitDesc;

    /**
     * 级别
     */
    @TableField("level")
    private String level;

    /**
     * 承办年份
     */
    @TableField("year")
    private String year;

    /**
     * 承办能力(会议规模)
     */
    @TableField("ability")
    private String ability;

    /**
     * 论坛主题
     */
    @TableField("topic")
    private String topic;

    /**
     * 负责人
     */
    @TableField("manager")
    private String manager;

    /**
     * 负责人电话
     */
    @TableField("mana_phone")
    private String manaPhone;

    /**
     * 负责人邮箱
     */
    @TableField("mana_email")
    private String manaEmail;

    /**
     * 组织保障
     */
    @TableField("org_sup")
    private String orgSup;

    /**
     * 经费保障
     */
    @TableField("funds_sup")
    private String fundsSup;

    /**
     * 人员保障
     */
    @TableField("staff_sup")
    private String staffSup;

    /**
     * 办会经验
     */
    @TableField("experience")
    private String experience;

    /**
     * 是否同意论坛章程; 0-不同意,1-同意
     */
    @TableField("agree_rule")
    private Integer agreeRule;

    /**
     * 承办方案附件路径
     */
    @TableField("plan_path")
    private String planPath;

    /**
     * 承诺书路径
     */
    @TableField("commit_path")
    private String commitPath;

    /**
     * 申报时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 申报人ID
     */
    @TableField("apply_user")
    private Long applyUser;

    /**
     * 承办方案附件名称
     */
    @TableField("plan_name")
    private String planName;

    /**
     * 承诺书附件名称
     */
    @TableField("commit_name")
    private String commitName;


    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getForumDesc() {
        return forumDesc;
    }

    public void setForumDesc(String forumDesc) {
        this.forumDesc = forumDesc;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManaPhone() {
        return manaPhone;
    }

    public void setManaPhone(String manaPhone) {
        this.manaPhone = manaPhone;
    }

    public String getManaEmail() {
        return manaEmail;
    }

    public void setManaEmail(String manaEmail) {
        this.manaEmail = manaEmail;
    }

    public String getOrgSup() {
        return orgSup;
    }

    public void setOrgSup(String orgSup) {
        this.orgSup = orgSup;
    }

    public String getFundsSup() {
        return fundsSup;
    }

    public void setFundsSup(String fundsSup) {
        this.fundsSup = fundsSup;
    }

    public String getStaffSup() {
        return staffSup;
    }

    public void setStaffSup(String staffSup) {
        this.staffSup = staffSup;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Integer getAgreeRule() {
        return agreeRule;
    }

    public void setAgreeRule(Integer agreeRule) {
        this.agreeRule = agreeRule;
    }

    public String getPlanPath() {
        return planPath;
    }

    public void setPlanPath(String planPath) {
        this.planPath = planPath;
    }

    public String getCommitPath() {
        return commitPath;
    }

    public void setCommitPath(String commitPath) {
        this.commitPath = commitPath;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Long getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Long applyUser) {
        this.applyUser = applyUser;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    @Override
    public String toString() {
        return "HoldForum{" +
                "forumId=" + forumId +
                ", forumName=" + forumName +
                ", forumDesc=" + forumDesc +
                ", applyStatus=" + applyStatus +
                ", unitName=" + unitName +
                ", unitDesc=" + unitDesc +
                ", level=" + level +
                ", year=" + year +
                ", ability=" + ability +
                ", topic=" + topic +
                ", manager=" + manager +
                ", manaPhone=" + manaPhone +
                ", manaEmail=" + manaEmail +
                ", orgSup=" + orgSup +
                ", fundsSup=" + fundsSup +
                ", staffSup=" + staffSup +
                ", experience=" + experience +
                ", agreeRule=" + agreeRule +
                ", planPath=" + planPath +
                ", commitPath=" + commitPath +
                ", applyTime=" + applyTime +
                ", applyUser=" + applyUser +
                ", planName=" + planName +
                ", commitName=" + commitName +
                "}";
    }
}
