package cn.stylefeng.guns.expert.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 评审专家表
 * </p>
 *
 * @author wucy
 * @since 2020-05-11
 */
@Data
public class ReviewMajorParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 专家ID
     */
    private Long reviewId;

    /**
     * 专家名称
     */
    private String direct;

    /**
     * 论文分配数量
     */
    private Integer thesisCount;

    /**
     * 论文评审数量
     */
    private Integer reviewCount;

    /**
     * 退回数量
     */
    private Integer refuseCount;

    /**
     * 专家领域
     */
    private String majorType;

    /**
     * 来源
     */
    private String applyFrom;

    /**
     * 评审状态
     */
    private String checkStatus;

    /**
     * 创建时间
     */
    private Date applyTime;

    /**
     * 通过时间
     */
    private Date agreeTime;

    /**
     * 驳回时间
     */
    private Date refuseTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 所属领域
     */
    private String belongDomain;

    /**
     * 证件类型
     */
    private String idCardType;

    /**
     * 证件号
     */
    private String idCard;

    /**
     * 开户省份
     */
    private String accountProvince;

    /**
     * 开户城市
     */
    private String accountCity;

    /**
     * 银行机构
     */
    private String bank;

    /**
     * 联行号
     */
    private String unionNumber;

    /**
     * 开户行
     */
    private String accountBank;

    /**
     * 个人账号
     */
    private String personalAccount;

    @Override
    public String checkParam() {
        return null;
    }

}
