package cn.stylefeng.guns.modular.greatReviewMiddle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 著作专家中间表
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
@TableName("great_review_middle")
public class GreatReviewMiddle implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 成果专家中间表ID
     */
      @TableId(value = "middle_id", type = IdType.ID_WORKER)
    private Long middleId;

    /**
     * 专家ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 成果ID
     */
    @TableField("result_id")
    private Long resultId;

    /**
     * 分数
     */
    @TableField("score")
    private Integer score;

    /**
     * 评审结果 0-不同意参会，1-同意参会 2-评审中
     */
    @TableField("review_result")
    private Integer reviewResult;

    /**
     * 评审备注
     */
    @TableField("description")
    private String description;

    /**
     * 评审时间
     */
    @TableField("review_time")
    private Date reviewTime;


    public Long getMiddleId() {
        return middleId;
    }

    public void setMiddleId(Long middleId) {
        this.middleId = middleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Integer reviewResult) {
        this.reviewResult = reviewResult;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    @Override
    public String toString() {
        return "GreatReviewMiddle{" +
        "middleId=" + middleId +
        ", userId=" + userId +
        ", resultId=" + resultId +
        ", score=" + score +
        ", reviewResult=" + reviewResult +
        ", description=" + description +
        ", reviewTime=" + reviewTime +
        "}";
    }
}
