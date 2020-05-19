package cn.stylefeng.guns.modular.educationResult.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 优秀成果表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
@TableName("education_result")
public class EducationResult implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 成果ID
     */
      @TableId(value = "result_id", type = IdType.ID_WORKER)
    private Long resultId;

    /**
     * 成果名称
     */
    @TableField("result_name")
    private String resultName;

    /**
     * 申请方式; 1-个人, 2-单位
     */
    @TableField("apply_type")
    private Integer applyType;

    /**
     * 负责人姓名
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
     * 负责人职称/职务
     */
    @TableField("mana_post")
    private String manaPost;

    /**
     * 负责人研究方向
     */
    @TableField("mana_direct")
    private String manaDirect;

    /**
     * 成果意义
     */
    @TableField("result_mean")
    private String resultMean;

    /**
     * 应用价值
     */
    @TableField("value")
    private String value;

    /**
     * 应用范围
     */
    @TableField("range")
    private String range;

    /**
     * 服务对象
     */
    @TableField("object")
    private String object;

    /**
     * 攻关团队
     */
    @TableField("team")
    private String team;

    /**
     * 研究结论与成果
     */
    @TableField("conclusion")
    private String conclusion;

    /**
     * 过程简介
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 成果影响力
     */
    @TableField("influence")
    private String influence;

    /**
     * 宣传口号
     */
    @TableField("slogan")
    private String slogan;

    /**
     * 易拉宝设计图路径
     */
    @TableField("design_img")
    private String designImg;

    /**
     * 关键词
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 专家推荐信附件路径
     */
    @TableField("letter_path")
    private String letterPath;

    /**
     * 原创承诺书路径
     */
    @TableField("commit_path")
    private String commitPath;

    /**
     * 成果形式
     */
    @TableField("form")
    private String form;

    /**
     * 成果内容
     */
    @TableField("detail")
    private String detail;

    /**
     * 申报状态; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
     */
    @TableField("check_status")
    private Integer checkStatus;

    /**
     * 申请人/单位ID
     */
    @TableField("apply_id")
    private Long applyId;

    /**
     * 申请提交时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 申请驳回时间
     */
    @TableField("refuse_time")
    private Date refuseTime;

    /**
     * 审核通过时间
     */
    @TableField("pass_time")
    private Date passTime;

    /**
     * 取消申请时间
     */
    @TableField("cancel_time")
    private Date cancelTime;

    /**
     * 归属单位/个人
     */
    @TableField("belong_name")
    private String belongName;


    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
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

    public String getManaPost() {
        return manaPost;
    }

    public void setManaPost(String manaPost) {
        this.manaPost = manaPost;
    }

    public String getManaDirect() {
        return manaDirect;
    }

    public void setManaDirect(String manaDirect) {
        this.manaDirect = manaDirect;
    }

    public String getResultMean() {
        return resultMean;
    }

    public void setResultMean(String resultMean) {
        this.resultMean = resultMean;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getInfluence() {
        return influence;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getDesignImg() {
        return designImg;
    }

    public void setDesignImg(String designImg) {
        this.designImg = designImg;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLetterPath() {
        return letterPath;
    }

    public void setLetterPath(String letterPath) {
        this.letterPath = letterPath;
    }

    public String getCommitPath() {
        return commitPath;
    }

    public void setCommitPath(String commitPath) {
        this.commitPath = commitPath;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
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

    public Date getRefuseTime() {
        return refuseTime;
    }

    public void setRefuseTime(Date refuseTime) {
        this.refuseTime = refuseTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    @Override
    public String toString() {
        return "EducationResult{" +
        "resultId=" + resultId +
        ", resultName=" + resultName +
        ", applyType=" + applyType +
        ", manager=" + manager +
        ", manaPhone=" + manaPhone +
        ", manaEmail=" + manaEmail +
        ", manaPost=" + manaPost +
        ", manaDirect=" + manaDirect +
        ", resultMean=" + resultMean +
        ", value=" + value +
        ", range=" + range +
        ", object=" + object +
        ", team=" + team +
        ", conclusion=" + conclusion +
        ", introduce=" + introduce +
        ", influence=" + influence +
        ", slogan=" + slogan +
        ", designImg=" + designImg +
        ", keyword=" + keyword +
        ", letterPath=" + letterPath +
        ", commitPath=" + commitPath +
        ", form=" + form +
        ", detail=" + detail +
        ", checkStatus=" + checkStatus +
        ", applyId=" + applyId +
        ", applyTime=" + applyTime +
        ", refuseTime=" + refuseTime +
        ", passTime=" + passTime +
        ", cancelTime=" + cancelTime +
        ", belongName=" + belongName +
        "}";
    }
}