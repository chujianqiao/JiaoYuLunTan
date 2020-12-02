package cn.stylefeng.guns.modular.checkIn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 报到签到表
 * </p>
 *
 * @author CHU
 * @since 2020-07-30
 */
@TableName("check_in")
public class CheckIn implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 报到签到ID
     */
      @TableId(value = "check_id", type = IdType.ID_WORKER)
    private Long checkId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 报到状态 0-未报到 1-已报到
     */
    @TableField("register_status")
    private Integer registerStatus;

    /**
     * 报到时间
     */
    @TableField("register_time")
    private Date registerTime;

    /**
     * 签到状态 0-未报到 1-已报到
     */
    @TableField("sign_status")
    private Integer signStatus;

    /**
     * 签到时间
     */
    @TableField("sign_time")
    private Date signTime;

    /**
     * 签到地点
     */
    @TableField("sign_place")
    private String signPlace;

    /**
     * 大会或者论坛 0-大会 1-论坛
     */
    @TableField("meet_or_forum")
    private Integer meetOrForum;

    /**
     * 论坛ID
     */
    @TableField("forum_id")
    private Long forumId;

    /**
     * 会议ID
     */
    @TableField("meet_id")
    private Long meetId;


    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Integer registerStatus) {
        this.registerStatus = registerStatus;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getSignPlace() {
        return signPlace;
    }

    public void setSignPlace(String signPlace) {
        this.signPlace = signPlace;
    }

    public Integer getMeetOrForum() {
        return meetOrForum;
    }

    public void setMeetOrForum(Integer meetOrForum) {
        this.meetOrForum = meetOrForum;
    }

    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }
    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    @Override
    public String toString() {
        return "CheckIn{" +
        "checkId=" + checkId +
        ", userId=" + userId +
        ", registerStatus=" + registerStatus +
        ", registerTime=" + registerTime +
        ", signStatus=" + signStatus +
        ", signTime=" + signTime +
        ", signPlace=" + signPlace +
        ", meetOrForum=" + meetOrForum +
        ", forumId=" + forumId +
        ", meetId=" + meetId +
        "}";
    }
}
