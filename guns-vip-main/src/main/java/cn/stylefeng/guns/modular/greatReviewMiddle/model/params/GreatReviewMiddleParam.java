package cn.stylefeng.guns.modular.greatReviewMiddle.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 著作专家中间表
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
@Data
public class GreatReviewMiddleParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 成果专家中间表ID
     */
    private Long middleId;

    /**
     * 专家ID
     */
    private Long userId;

    /**
     * 成果ID
     */
    private Long resultId;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 评审结果 0-不同意参会，1-同意参会 2-评审中
     */
    private Integer reviewResult;

    /**
     * 评审备注
     */
    private String description;

    /**
     * 评审时间
     */
    private Date reviewTime;

    @Override
    public String checkParam() {
        return null;
    }

}
