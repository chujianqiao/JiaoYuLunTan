package cn.stylefeng.guns.modular.forum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 分论坛表
 * </p>
 *
 * @author CHU
 * @since 2020-08-10
 */
@TableName("forum")
public class Forum implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 分论坛ID
     */
      @TableId(value = "forum_id", type = IdType.ID_WORKER)
    private Long forumId;

    /**
     * 分论坛名称
     */
    @TableField("forum_name")
    private String forumName;

    /**
     * 设置人数
     */
    @TableField("set_num")
    private Integer setNum;

    /**
     * 已有人数
     */
    @TableField("exist_num")
    private Integer existNum;

    /**
     * 发布状态 0-未发布 1-已发布
     */
    @TableField("status")
    private Integer status;

    /**
     * 注册开始时间
     */
    @TableField("register_start_time")
    private Date registerStartTime;

    /**
     * 注册结束时间
     */
    @TableField("register_end_time")
    private Date registerEndTime;

    /**
     * 参会开始时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 参会结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 论坛地点
     */
    @TableField("location")
    private String location;

    /**
     * 会议ID
     */
    @TableId(value = "meet_id")
    private Long meetId;


    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public Integer getSetNum() {
        return setNum;
    }

    public void setSetNum(Integer setNum) {
        this.setNum = setNum;
    }

    public Integer getExistNum() {
        return existNum;
    }

    public void setExistNum(Integer existNum) {
        this.existNum = existNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegisterStartTime() {
        return registerStartTime;
    }

    public void setRegisterStartTime(Date registerStartTime) {
        this.registerStartTime = registerStartTime;
    }

    public Date getRegisterEndTime() {
        return registerEndTime;
    }

    public void setRegisterEndTime(Date registerEndTime) {
        this.registerEndTime = registerEndTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    @Override
    public String toString() {
        return "Forum{" +
        "forumId=" + forumId +
        ", forumName=" + forumName +
        ", setNum=" + setNum +
        ", existNum=" + existNum +
        ", status=" + status +
        ", registerStartTime=" + registerStartTime +
        ", registerEndTime=" + registerEndTime +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", location=" + location +
        ", meetId=" + meetId +
        "}";
    }
}
