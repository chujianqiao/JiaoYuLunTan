package cn.stylefeng.guns.thesisReviewMiddle.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 论文专家中间表
 * </p>
 *
 * @author CHU
 * @since 2020-08-05
 */
@Data
public class ThesisReviewMiddleResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 论文专家中间表ID
     */
    private Long middleId;

    /**
     * 专家ID
     */
    private Long userId;

    /**
     * 论文ID
     */
    private Long thesisId;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 评审时间
     */
    private Date reviewTime;

    /**
     * 评审顺序  1-初评  2-复评
     */
    private Integer reviewSort;

}
