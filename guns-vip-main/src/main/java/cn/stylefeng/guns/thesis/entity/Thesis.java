package cn.stylefeng.guns.thesis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 论文表
 * </p>
 *
 * @author wucy
 * @since 2020-05-21
 */
@TableName("thesis")
public class Thesis implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 论文ID
     */
      @TableId(value = "thesis_id", type = IdType.ID_WORKER)
    private Long thesisId;

    /**
     * 论文题目
     */
    @TableField("thesis_title")
    private String thesisTitle;

    /**
     * 英文题目
     */
    @TableField("eng_title")
    private String engTitle;

    /**
     * 论文作者ID
     */
    @TableField("thesis_user")
    private String thesisUser;

    /**
     * 评审字典项
     */
    @TableField("status")
    private String status;

    /**
     * 评审结果; 0-不通过，1-通过
     */
    @TableField("review_result")
    private Integer reviewResult;

    /**
     * 是否推荐优秀; 0-否，1-是
     */
    @TableField("isgreat")
    private Integer isgreat;

    /**
     * 推荐专家人数
     */
    @TableField("great_num")
    private Integer greatNum;

    /**
     * 推优专家ID,多个用逗号隔开
     */
    @TableField("great_id")
    private String greatId;

    /**
     * 论文提交时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 正文
     */
    @TableField("thesis_text")
    private String thesisText;

    /**
     * 分数
     */
    @TableField("score")
    private Integer score;

    /**
     * 评审人ID,多个用逗号隔开
     */
    @TableField("review_user")
    private String reviewUser;

    /**
     * 是否为优秀论文;0-否,1-是
     */
    @TableField("great")
    private Integer great;

    /**
     * 中文关键词
     */
    @TableField("cn_keyword")
    private String cnKeyword;

    /**
     * 英文关键词
     */
    @TableField("eng_keyword")
    private String engKeyword;

    /**
     * 中文摘要
     */
    @TableField("cn_abstract")
    private String cnAbstract;

    /**
     * 英文摘要
     */
    @TableField("eng_abstract")
    private String engAbstract;

    /**
     * 参会论文研究方向
     */
    @TableField("thesis_direct")
    private String thesisDirect;

    /**
     * 论文附件路径
     */
    @TableField("thesis_path")
    private String thesisPath;

    /**
     * 论文附件文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 所属领域
     */
    @TableField("belong_domain")
    private String belongDomain;

    /**
     * 论文评审时间
     */
    @TableField("review_time")
    private Date reviewTime;


    public Long getThesisId() {
        return thesisId;
    }

    public void setThesisId(Long thesisId) {
        this.thesisId = thesisId;
    }

    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    public String getThesisUser() {
        return thesisUser;
    }

    public void setThesisUser(String thesisUser) {
        this.thesisUser = thesisUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Integer reviewResult) {
        this.reviewResult = reviewResult;
    }

    public Integer getIsgreat() {
        return isgreat;
    }

    public void setIsgreat(Integer isgreat) {
        this.isgreat = isgreat;
    }

    public Integer getGreatNum() {
        return greatNum;
    }

    public void setGreatNum(Integer greatNum) {
        this.greatNum = greatNum;
    }

    public String getGreatId() {
        return greatId;
    }

    public void setGreatId(String greatId) {
        this.greatId = greatId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getThesisText() {
        return thesisText;
    }

    public void setThesisText(String thesisText) {
        this.thesisText = thesisText;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public Integer getGreat() {
        return great;
    }

    public void setGreat(Integer great) {
        this.great = great;
    }

    public String getCnKeyword() {
        return cnKeyword;
    }

    public void setCnKeyword(String cnKeyword) {
        this.cnKeyword = cnKeyword;
    }

    public String getEngKeyword() {
        return engKeyword;
    }

    public void setEngKeyword(String engKeyword) {
        this.engKeyword = engKeyword;
    }

    public String getCnAbstract() {
        return cnAbstract;
    }

    public void setCnAbstract(String cnAbstract) {
        this.cnAbstract = cnAbstract;
    }

    public String getEngAbstract() {
        return engAbstract;
    }

    public void setEngAbstract(String engAbstract) {
        this.engAbstract = engAbstract;
    }

    public String getThesisDirect() {
        return thesisDirect;
    }

    public void setThesisDirect(String thesisDirect) {
        this.thesisDirect = thesisDirect;
    }

    public String getThesisPath() {
        return thesisPath;
    }

    public void setThesisPath(String thesisPath) {
        this.thesisPath = thesisPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBelongDomain() {
        return belongDomain;
    }

    public void setBelongDomain(String belongDomain) {
        this.belongDomain = belongDomain;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }
    @Override
    public String toString() {
        return "Thesis{" +
        "thesisId=" + thesisId +
        ", thesisTitle=" + thesisTitle +
        ", engTitle=" + engTitle +
        ", thesisUser=" + thesisUser +
        ", status=" + status +
        ", reviewResult=" + reviewResult +
        ", isgreat=" + isgreat +
        ", greatNum=" + greatNum +
        ", greatId=" + greatId +
        ", applyTime=" + applyTime +
        ", thesisText=" + thesisText +
        ", score=" + score +
        ", reviewUser=" + reviewUser +
        ", great=" + great +
        ", cnKeyword=" + cnKeyword +
        ", engKeyword=" + engKeyword +
        ", cnAbstract=" + cnAbstract +
        ", engAbstract=" + engAbstract +
        ", thesisDirect=" + thesisDirect +
        ", thesisPath=" + thesisPath +
        ", fileName=" + fileName +
        ", belongDomain=" + belongDomain +
        ", reviewTime=" + reviewTime +
        "}";
    }
}
