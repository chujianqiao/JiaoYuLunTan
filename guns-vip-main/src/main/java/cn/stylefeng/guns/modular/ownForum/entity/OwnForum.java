package cn.stylefeng.guns.modular.ownForum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 自设论坛表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
@TableName("own_forum")
public class OwnForum implements Serializable {

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
     * 申报种类; 1-个人,2-单位
     */
    @TableField("apply_type")
    private Integer applyType;

    /**
     * 申报状态; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请

     */
    @TableField("apply_status")
    private Integer applyStatus;

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
     * 分论坛ID,多个ID用逗号隔开
     */
    @TableField("sub_id")
    private String subId;

    /**
     * 是否有课题团队; 0-否,1-是
     */
    @TableField("issubject")
    private Integer issubject;

    /**
     * 课题级别
     */
    @TableField("subject_lev")
    private String subjectLev;

    /**
     * 课题名称
     */
    @TableField("subject_name")
    private String subjectName;

    /**
     * 论坛申报方案附件路径
     */
    @TableField("plan_path")
    private String planPath;

    /**
     * 与大会主题的关系
     */
    @TableField("relation")
    private String relation;

    /**
     * 选题意义
     */
    @TableField("meaning")
    private String meaning;

    /**
     * 拟邀请专家姓名
     */
    @TableField("expert_name")
    private String expertName;

    /**
     * 参会人员类型
     */
    @TableField("staff_type")
    private String staffType;

    /**
     * 组织形式
     */
    @TableField("org_type")
    private String orgType;

    /**
     * 人员分工表附件路径
     */
    @TableField("divide_path")
    private String dividePath;

    /**
     * 是否同意论坛章程;0-不同意,1-同意
     */
    @TableField("agree_rule")
    private Integer agreeRule;

    /**
     * 申报人ID
     */
    @TableField("apply_id")
    private Long applyId;

    /**
     * 申报时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 人员分工表附件名称
     */
    @TableField("divide_name")
    private String divideName;

    /**
     * 申报方案附件名称
     */
    @TableField("plan_name")
    private String planName;

    /**
     * 论坛主题名称
     */
    @TableField("forum_topic")
    private String forumTopic;

    /**
     * 论坛计划规模
     */
    @TableField("forum_size")
    private String forumSize;

    /**
     * 单位名称
     */
    @TableField("unit_name")
    private String unitName;

    /**
     * 研究方向
     */
    @TableField("direction")
    private String direction;

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

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
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

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public Integer getIssubject() {
        return issubject;
    }

    public void setIssubject(Integer issubject) {
        this.issubject = issubject;
    }

    public String getSubjectLev() {
        return subjectLev;
    }

    public void setSubjectLev(String subjectLev) {
        this.subjectLev = subjectLev;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPlanPath() {
        return planPath;
    }

    public void setPlanPath(String planPath) {
        this.planPath = planPath;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getDividePath() {
        return dividePath;
    }

    public void setDividePath(String dividePath) {
        this.dividePath = dividePath;
    }

    public Integer getAgreeRule() {
        return agreeRule;
    }

    public void setAgreeRule(Integer agreeRule) {
        this.agreeRule = agreeRule;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getDivideName() {
        return divideName;
    }

    public void setDivideName(String divideName) {
        this.divideName = divideName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getForumTopic() {
        return forumTopic;
    }

    public void setForumTopic(String forumTopic) {
        this.forumTopic = forumTopic;
    }

    public String getForumSize() {
        return forumSize;
    }

    public void setForumSize(String forumSize) {
        this.forumSize = forumSize;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    @Override
    public String toString() {
        return "OwnForum{" +
        "forumId=" + forumId +
        ", forumName=" + forumName +
        ", forumDesc=" + forumDesc +
        ", applyType=" + applyType +
        ", applyStatus=" + applyStatus +
        ", manager=" + manager +
        ", manaPhone=" + manaPhone +
        ", manaEmail=" + manaEmail +
        ", subId=" + subId +
        ", issubject=" + issubject +
        ", subjectLev=" + subjectLev +
        ", subjectName=" + subjectName +
        ", planPath=" + planPath +
        ", relation=" + relation +
        ", meaning=" + meaning +
        ", expertName=" + expertName +
        ", staffType=" + staffType +
        ", orgType=" + orgType +
        ", dividePath=" + dividePath +
        ", agreeRule=" + agreeRule +
        ", applyId=" + applyId +
        ", applyTime=" + applyTime +
        ", divideName=" + divideName +
        ", planName=" + planName +
        ", forumTopic=" + forumTopic +
        ", forumSize=" + forumSize +
        ", unitName=" + unitName +
        ", direction=" + direction +
        ", title=" + title +
        ", post=" + post +
        "}";
    }
}
