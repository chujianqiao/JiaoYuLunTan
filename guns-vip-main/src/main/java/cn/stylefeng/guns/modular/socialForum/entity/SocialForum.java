package cn.stylefeng.guns.modular.socialForum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 社会资助论坛表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-15
 */
@TableName("social_forum")
public class SocialForum implements Serializable {

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
     * 申报企业/单位名称
     */
    @TableField("unit_name")
    private String unitName;

    /**
     * 企业/单位所在地
     */
    @TableField("unit_place")
    private String unitPlace;

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
     * 已资助的会议
     */
    @TableField("already_meet")
    private String alreadyMeet;

    /**
     * 拟资助版块
     */
    @TableField("sup_plate")
    private String supPlate;

    /**
     * 资助金额
     */
    @TableField("sup_money")
    private Integer supMoney;

    /**
     * 合同条件附件路径
     */
    @TableField("contract_path")
    private String contractPath;

    /**
     * 申报时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 申报单位ID
     */
    @TableField("apply_id")
    private Long applyId;

    /**
     * 合同条件附件名称
     */
    @TableField("contract_name")
    private String contractName;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 统一社会信用代码
     */
    @TableField("credit_code")
    private String creditCode;

    /**
     * 会议ID
     */
    @TableField("meet_id")
    private Long meetId;


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

    public String getUnitPlace() {
        return unitPlace;
    }

    public void setUnitPlace(String unitPlace) {
        this.unitPlace = unitPlace;
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

    public String getAlreadyMeet() {
        return alreadyMeet;
    }

    public void setAlreadyMeet(String alreadyMeet) {
        this.alreadyMeet = alreadyMeet;
    }

    public String getSupPlate() {
        return supPlate;
    }

    public void setSupPlate(String supPlate) {
        this.supPlate = supPlate;
    }

    public Integer getSupMoney() {
        return supMoney;
    }

    public void setSupMoney(Integer supMoney) {
        this.supMoney = supMoney;
    }

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    @Override
    public String toString() {
        return "SocialForum{" +
        "forumId=" + forumId +
        ", forumName=" + forumName +
        ", forumDesc=" + forumDesc +
        ", applyStatus=" + applyStatus +
        ", unitName=" + unitName +
        ", unitPlace=" + unitPlace +
        ", manager=" + manager +
        ", manaPhone=" + manaPhone +
        ", manaEmail=" + manaEmail +
        ", alreadyMeet=" + alreadyMeet +
        ", supPlate=" + supPlate +
        ", supMoney=" + supMoney +
        ", contractPath=" + contractPath +
        ", applyTime=" + applyTime +
        ", applyId=" + applyId +
        ", contractName=" + contractName +
        ", remarks=" + remarks +
        ", creditCode=" + creditCode +
        ", meetId=" + meetId +
        "}";
    }
}
