package cn.stylefeng.guns.reviewunit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 理事单位表
 * </p>
 *
 * @author wucy
 * @since 2020-05-14
 */
@TableName("review_unit")
public class ReviewUnit implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 单位ID
     */
      @TableId(value = "review_id", type = IdType.ID_WORKER)
    private Long reviewId;

    /**
     * 单位所在地
     */
    @TableField("location")
    private String location;

    /**
     * 担任理事单位年份
     */
    @TableField("year")
    private String year;

    /**
     * 代表姓名
     */
    @TableField("rep_name")
    private String repName;

    /**
     * 代表职称/职务
     */
    @TableField("post")
    private String post;

    /**
     * 学历
     */
    @TableField("education")
    private String education;

    /**
     * 导入时间
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ReviewUnit{" +
        "reviewId=" + reviewId +
        ", location=" + location +
        ", year=" + year +
        ", repName=" + repName +
        ", post=" + post +
        ", education=" + education +
        ", createTime=" + createTime +
        "}";
    }
}
