package cn.stylefeng.guns.collTopic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 论坛主题征集表
 * </p>
 *
 * @author wucy
 * @since 2020-05-18
 */
@TableName("collect_topic")
public class CollectTopic implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主题ID
     */
      @TableId(value = "topic_id", type = IdType.ID_WORKER)
    private Long topicId;

    /**
     * 创建人ID
     */
      @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 大会主题
     */
    @TableField("topic_name")
    private String topicName;

    /**
     * 选题意义
     */
    @TableField("topic_desc")
    private String topicDesc;

    /**
     * 分论坛主题
     */
    @TableField("sub_topic")
    private String subTopic;

    /**
     * 票数
     */
    @TableField("vote_num")
    private Integer voteNum;

    /**
     * 创建日期
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 管理员自定义备用字段1
     */
    @TableField("diy1")
    private String diy1;

    /**
     * 管理员自定义备用字段2
     */
    @TableField("diy2")
    private String diy2;


    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDiy1() {
        return diy1;
    }

    public void setDiy1(String diy1) {
        this.diy1 = diy1;
    }

    public String getDiy2() {
        return diy2;
    }

    public void setDiy2(String diy2) {
        this.diy2 = diy2;
    }

    @Override
    public String toString() {
        return "CollectTopic{" +
        "topicId=" + topicId +
        ", createUser=" + createUser +
        ", topicName=" + topicName +
        ", topicDesc=" + topicDesc +
        ", subTopic=" + subTopic +
        ", voteNum=" + voteNum +
        ", createTime=" + createTime +
        ", diy1=" + diy1 +
        ", diy2=" + diy2 +
        "}";
    }
}
