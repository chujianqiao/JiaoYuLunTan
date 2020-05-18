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
     * 研究方向和专长
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
     * 专家分类
     */
    private String majorType;

    /**
     * 来源
     */
    private String applyFrom;

    /**
     * 申报状态; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
     */
    private Integer checkStatus;

    /**
     * 提交申请时间
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

    @Override
    public String checkParam() {
        return null;
    }

}
