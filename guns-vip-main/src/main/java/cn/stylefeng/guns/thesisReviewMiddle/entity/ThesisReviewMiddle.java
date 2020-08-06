package cn.stylefeng.guns.thesisReviewMiddle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 论文专家中间表
 * </p>
 *
 * @author CHU
 * @since 2020-08-05
 */
@TableName("thesis_review_middle")
public class ThesisReviewMiddle implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 论文专家中间表ID
     */
      @TableId(value = "middle_id", type = IdType.ID_WORKER)
    private Long middleId;

    /**
     * 专家ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 论文ID
     */
    @TableField("thesis_id")
    private Long thesisId;

    /**
     * 分数
     */
    @TableField("score")
    private Integer score;

    /**
     * 评审时间
     */
    @TableField("review_time")
    private Date reviewTime;

    /**
     * 评审顺序  1-初评  2-复评
     */
    @TableField("review_sort")
    private Integer reviewSort;


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

    public Long getThesisId() {
        return thesisId;
    }

    public void setThesisId(Long thesisId) {
        this.thesisId = thesisId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getReviewSort() {
        return reviewSort;
    }

    public void setReviewSort(Integer reviewSort) {
        this.reviewSort = reviewSort;
    }

    @Override
    public String toString() {
        return "ThesisReviewMiddle{" +
        "middleId=" + middleId +
        ", userId=" + userId +
        ", thesisId=" + thesisId +
        ", score=" + score +
        ", reviewTime=" + reviewTime +
        ", reviewSort=" + reviewSort +
        "}";
    }
}
