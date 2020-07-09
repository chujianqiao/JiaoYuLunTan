package cn.stylefeng.guns.expert.model.result;

import lombok.Data;
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
public class ReviewMajorResult implements Serializable {

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
     * 评审状态
     */
    private String checkStatus;

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


    /**
     * 所属领域
     */
    private String belongDomain;

}
