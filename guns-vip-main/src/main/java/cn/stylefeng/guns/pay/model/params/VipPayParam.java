package cn.stylefeng.guns.pay.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 会员缴费表
 * </p>
 *
 * @author wucy
 * @since 2020-07-15
 */
@Data
public class VipPayParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 缴费记录ID
     */
    private Long payId;

    /**
     * 缴费用户ID
     */
    private Long payUser;

    /**
     * memberId
     */
    private Long memberId;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 缴费金额
     */
    private BigDecimal payMoney;

    /**
     * 缴费方式;(alipay/wechat)
     */
    private String payType;

    /**
     * 平台交易单号
     */
    private String tranNum;

    /**
     * 缴费时间
     */
    private Date payTime;

    private Long meetId;

    @Override
    public String checkParam() {
        return null;
    }

}
