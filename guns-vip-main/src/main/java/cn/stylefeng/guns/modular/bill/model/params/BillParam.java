package cn.stylefeng.guns.modular.bill.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author CHU
 * @since 2020-11-05
 */
@Data
public class BillParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 发票ID
     */
    private Long billId;

    /**
     * 申请人ID
     */
    private Long userId;

    /**
     * 会议ID
     */
    private Long meetMemberId;

    /**
     * 企业名称
     */
    private String enterprise;

    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;

    /**
     * 凭证名称
     */
    private String credentialsName;

    /**
     * 凭证路径
     */
    private String credentialsPath;

    /**
     * 饮食禁忌
     */
    private String food;

    /**
     * 酒店预订
     */
    private String hotel;

    @Override
    public String checkParam() {
        return null;
    }

}
