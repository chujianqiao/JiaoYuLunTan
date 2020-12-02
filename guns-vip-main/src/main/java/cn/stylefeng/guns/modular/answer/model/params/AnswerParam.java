package cn.stylefeng.guns.modular.answer.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 自动回复表
 * </p>
 *
 * @author CHU
 * @since 2020-11-09
 */
@Data
public class AnswerParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 自动回复ID
     */
    private Long answerId;

    /**
     * 问题
     */
    private String answerKey;

    /**
     * 答案
     */
    private String answerValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private String status;

    @Override
    public String checkParam() {
        return null;
    }

}
