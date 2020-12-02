package cn.stylefeng.guns.modular.answer.model.result;

import lombok.Data;
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
public class AnswerResult implements Serializable {

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

}
