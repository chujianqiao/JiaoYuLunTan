package cn.stylefeng.guns.modular.answer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 自动回复表
 * </p>
 *
 * @author CHU
 * @since 2020-11-09
 */
@TableName("answer")
public class Answer implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动回复ID
     */
      @TableId(value = "answer_id", type = IdType.ID_WORKER)
    private Long answerId;

    /**
     * 问题
     */
    @TableField("answer_key")
    private String answerKey;

    /**
     * 答案
     */
    @TableField("answer_value")
    private String answerValue;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 创建时间
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 状态
     */
    @TableField("status")
    private String status;


    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Answer{" +
        "answerId=" + answerId +
        ", answerKey=" + answerKey +
        ", answerValue=" + answerValue +
        ", sort=" + sort +
        ", createTime=" + createTime +
        ", status=" + status +
        "}";
    }
}
