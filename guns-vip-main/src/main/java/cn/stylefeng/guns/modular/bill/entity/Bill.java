package cn.stylefeng.guns.modular.bill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
     * 会议ID
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
        "}";
    }
}
