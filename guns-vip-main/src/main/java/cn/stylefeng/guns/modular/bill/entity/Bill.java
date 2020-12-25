package cn.stylefeng.guns.modular.bill.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author CHU
 * @since 2020-11-05
 */
@TableName("bill")
public class Bill implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 发票ID
     */
      @TableId(value = "bill_id", type = IdType.ID_WORKER)
    private Long billId;

    /**
     * 申请人ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 会议成员ID
     */
    @TableField("meet_member_id")
    private Long meetMemberId;

    /**
     * 企业名称
     */
    @TableField("enterprise")
    private String enterprise;

    /**
     * 纳税人识别号
     */
    @TableField("taxpayer_number")
    private String taxpayerNumber;

    /**
     * 凭证名称
     */
    @TableField("credentials_name")
    private String credentialsName;

    /**
     * 凭证路径
     */
    @TableField("credentials_path")
    private String credentialsPath;

    /**
     * 饮食禁忌
     */
    @TableField("food")
    private String food;

    /**
     * 酒店预订
     */
    @TableField("hotel")
    private String hotel;

    /**
     * 联系人
     */
    @TableField("contact")
    private String contact;

    /**
     * 联系电话
     */
    @TableField("bill_phone")
    private String billPhone;

    /**
     * 接收邮箱
     */
    @TableField("bill_email")
    private String billEmail;

    /**
     * 接收地址
     */
    @TableField("address")
    private String address;

    /**
     * 备注
     */
    @TableField("bill_remark")
    private String billRemark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 会议ID
     */
    @TableField("meet_id")
    private Long meetId;


    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMeetMemberId() {
        return meetMemberId;
    }

    public void setMeetMemberId(Long meetMemberId) {
        this.meetMemberId = meetMemberId;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getCredentialsName() {
        return credentialsName;
    }

    public void setCredentialsName(String credentialsName) {
        this.credentialsName = credentialsName;
    }

    public String getCredentialsPath() {
        return credentialsPath;
    }

    public void setCredentialsPath(String credentialsPath) {
        this.credentialsPath = credentialsPath;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBillPhone() {
        return billPhone;
    }

    public void setBillPhone(String billPhone) {
        this.billPhone = billPhone;
    }

    public String getBillEmail() {
        return billEmail;
    }

    public void setBillEmail(String billEmail) {
        this.billEmail = billEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }


    @Override
    public String toString() {
        return "Bill{" +
        "billId=" + billId +
        ", userId=" + userId +
        ", meetMemberId=" + meetMemberId +
        ", enterprise=" + enterprise +
        ", taxpayerNumber=" + taxpayerNumber +
        ", credentialsName=" + credentialsName +
        ", credentialsPath=" + credentialsPath +
        ", food=" + food +
        ", hotel=" + hotel +
        ", contact=" + contact +
        ", billPhone=" + billPhone +
        ", billEmail=" + billEmail +
        ", address=" + address +
        ", billRemark=" + billRemark +
        ", meetId=" + meetId +
        ", createTime=" + createTime +
        "}";
    }
}
