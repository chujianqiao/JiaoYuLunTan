package cn.stylefeng.guns.meetRegister.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 会议注册成员表
 * </p>
 *
 * @author wucy
 * @since 2020-05-20
 */
@TableName("meet_member")
public class MeetMember implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "member_id", type = IdType.ID_WORKER)
    private Long memberId;

    /**
     * 参会人员ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 参会论文ID
     */
    @TableField("thesis_id")
    private Long thesisId;

    /**
     * 是否申请发言; 0-否,1-是
     */
    @TableField("speak")
    private Integer speak;

    /**
     * 是否同意参加形势研判会;0-否，1-是(只有教授可以选择)
     */
    @TableField("judge")
    private Integer judge;

    /**
     * 自设论坛ID
     */
    @TableField("own_forumid")
    private Long ownForumid;

    /**
     * 注册时间
     */
    @TableField("reg_time")
    private Date regTime;




    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Integer getSpeak() {
        return speak;
    }

    public void setSpeak(Integer speak) {
        this.speak = speak;
    }

    public Integer getJudge() {
        return judge;
    }

    public void setJudge(Integer judge) {
        this.judge = judge;
    }

    public Long getOwnForumid() {
        return ownForumid;
    }

    public void setOwnForumid(Long ownForumid) {
        this.ownForumid = ownForumid;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "MeetMember{" +
        "memberId=" + memberId +
        ", userId=" + userId +
        ", thesisId=" + thesisId +
        ", speak=" + speak +
        ", judge=" + judge +
        ", ownForumid=" + ownForumid +
        ", regTime=" + regTime +
        "}";
    }
}
