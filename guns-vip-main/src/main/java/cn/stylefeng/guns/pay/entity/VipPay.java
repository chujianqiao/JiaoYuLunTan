package cn.stylefeng.guns.pay.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 会员缴费表
 * </p>
 *
 * @author wucy
 * @since 2020-07-15
 */
@TableName("vip_pay")
public class VipPay implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 缴费记录ID
     */
      @TableId(value = "pay_id", type = IdType.ID_WORKER)
    private Long payId;

    /**
     * 缴费用户ID
     */
    @TableField("pay_user")
    private Long payUser;

    /**
     * 订单号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 缴费金额
     */
    @TableField("pay_money")
    private BigDecimal payMoney;

    /**
     * 缴费方式;(alipay/wechat)
     */
    @TableField("pay_type")
    private String payType;

    /**
     * 平台交易单号
     */
    @TableField("tran_num")
    private String tranNum;

    /**
     * 缴费时间
     */
    @TableField("pay_time")
    private Date payTime;


    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Long getPayUser() {
        return payUser;
    }

    public void setPayUser(Long payUser) {
        this.payUser = payUser;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTranNum() {
        return tranNum;
    }

    public void setTranNum(String tranNum) {
        this.tranNum = tranNum;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "VipPay{" +
        "payId=" + payId +
        ", payUser=" + payUser +
        ", orderNum=" + orderNum +
        ", payMoney=" + payMoney +
        ", payType=" + payType +
        ", tranNum=" + tranNum +
        ", payTime=" + payTime +
        "}";
    }
}
