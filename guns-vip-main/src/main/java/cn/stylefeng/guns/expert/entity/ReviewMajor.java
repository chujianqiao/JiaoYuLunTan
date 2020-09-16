package cn.stylefeng.guns.expert.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 评审专家表
 * </p>
 *
 * @author wucy
 * @since 2020-05-11
 */
@TableName("review_major")
public class ReviewMajor implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 专家ID
     */
      @TableId(value = "review_id", type = IdType.ID_WORKER)
    private Long reviewId;

    /**
     * 研究方向和专长
     */
    @TableField("direct")
    private String direct;

    /**
     * 论文分配数量
     */
    @TableField("thesis_count")
    private Integer thesisCount;

    /**
     * 论文评审数量
     */
    @TableField("review_count")
    private Integer reviewCount;

    /**
     * 退回数量
     */
    @TableField("refuse_count")
    private Integer refuseCount;

    /**
     * 专家分类
     */
    @TableField("major_type")
    private String majorType;

    /**
     * 来源
     */
    @TableField("apply_from")
    private String applyFrom;

    /**
     * 评审状态
     */
    @TableField("check_status")
    private String checkStatus;

    /**
     * 创建时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 通过时间
     */
    @TableField("agree_time")
    private Date agreeTime;

    /**
     * 驳回时间
     */
    @TableField("refuse_time")
    private Date refuseTime;

    /**
     * 取消时间
     */
    @TableField("cancel_time")
    private Date cancelTime;

    /**
     * 所属领域
     */
    @TableField("belong_domain")
    private String belongDomain;

    /**
     * 证件类型
     */
    @TableField("id_card_type")
    private String idCardType;

    /**
     * 证件号
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 开户省份
     */
    @TableField("account_province")
    private String accountProvince;

    /**
     * 开户城市
     */
    @TableField("account_city")
    private String accountCity;

    /**
     * 银行机构
     */
    @TableField("bank")
    private String bank;

    /**
     * 联行号
     */
    @TableField("union_number")
    private String unionNumber;

    /**
     * 开户行
     */
    @TableField("account_bank")
    private String accountBank;

    /**
     * 个人账号
     */
    @TableField("personal_account")
    private String personalAccount;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public Integer getThesisCount() {
        return thesisCount;
    }

    public void setThesisCount(Integer thesisCount) {
        this.thesisCount = thesisCount;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(Integer refuseCount) {
        this.refuseCount = refuseCount;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getApplyFrom() {
        return applyFrom;
    }

    public void setApplyFrom(String applyFrom) {
        this.applyFrom = applyFrom;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(Date agreeTime) {
        this.agreeTime = agreeTime;
    }

    public Date getRefuseTime() {
        return refuseTime;
    }

    public void setRefuseTime(Date refuseTime) {
        this.refuseTime = refuseTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getBelongDomain() {
        return belongDomain;
    }

    public void setBelongDomain(String belongDomain) {
        this.belongDomain = belongDomain;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAccountProvince() {
        return accountProvince;
    }

    public void setAccountProvince(String accountProvince) {
        this.accountProvince = accountProvince;
    }

    public String getAccountCity() {
        return accountCity;
    }

    public void setAccountCity(String accountCity) {
        this.accountCity = accountCity;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getUnionNumber() {
        return unionNumber;
    }

    public void setUnionNumber(String unionNumber) {
        this.unionNumber = unionNumber;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }

    @Override
    public String toString() {
        return "ReviewMajor{" +
        "reviewId=" + reviewId +
        ", direct=" + direct +
        ", thesisCount=" + thesisCount +
        ", reviewCount=" + reviewCount +
        ", refuseCount=" + refuseCount +
        ", majorType=" + majorType +
        ", applyFrom=" + applyFrom +
        ", checkStatus=" + checkStatus +
        ", applyTime=" + applyTime +
        ", agreeTime=" + agreeTime +
        ", refuseTime=" + refuseTime +
        ", cancelTime=" + cancelTime +
        ", belongDomain=" + belongDomain +
        ", idCardType=" + idCardType +
        ", idCard=" + idCard +
        ", accountProvince=" + accountProvince +
        ", accountCity=" + accountCity +
        ", bank=" + bank +
        ", unionNumber=" + unionNumber +
        ", accountBank=" + accountBank +
        ", personalAccount=" + personalAccount +
        "}";
    }
}
