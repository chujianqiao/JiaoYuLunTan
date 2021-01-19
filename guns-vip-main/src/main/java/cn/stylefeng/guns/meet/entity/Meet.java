package cn.stylefeng.guns.meet.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 会议表
 * </p>
 *
 * @author wucy
 * @since 2020-08-05
 */
@TableName("meet")
public class Meet implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 会议ID
     */
    @TableId(value = "meet_id", type = IdType.ID_WORKER)
    private Long meetId;

    /**
     * 会议名称
     */
    @TableField("meet_name")
    private String meetName;

    /**
     * 会议描述
     */
    @TableField("meet_desc")
    private String meetDesc;

    /**
     * 会议地点
     */
    @TableField("place")
    private String place;

    /**
     * 参会人数限制
     */
    @TableField("people_num")
    private Integer peopleNum;

    /**
     * 投稿人数限制
     */
    @TableField("thesis_num")
    private Integer thesisNum;

    /**
     * 开始时间
     */
    @TableField("begin_time")
    private Date beginTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 报名开始时间
     */
    @TableField("join_begtime")
    private Date joinBegTime;

    /**
     * 报名结束时间
     */
    @TableField("join_endtime")
    private Date joinEndTime;

    /**
     * 注册人
     */
    @TableField("reg_user")
    private Long regUser;

    /**
     * 注册时间
     */
    @TableField("reg_time")
    private Date regTime;

    /**
     * 会议状态
     * 0-未发布，1-已发布
     */
    @TableField("meet_status")
    private Integer meetStatus;

    /**
     * 富文本编辑器的内容
     */
    @TableField("content")
    private String content;

    /**
     * 报名费
     */
    @TableField("fee")
    private BigDecimal fee;

    /**
     * 大小会（大会-big,小会-small）
     */
    @TableField("meet_size")
    private String size;

    /**
     * 实际参会人数
     */
    @TableField("real_peo_num")
    private Integer realPeoNum;

    /**
     * 实际投稿人数
     */
    @TableField("real_the_num")
    private Integer realTheNum;


    /**
     * 是否必须提交论文
     * 0-非必须，1-必须
     */
    @TableField("must_sub")
    private Integer mustSub;


    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public String getMeetDesc() {
        return meetDesc;
    }

    public void setMeetDesc(String meetDesc) {
        this.meetDesc = meetDesc;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getThesisNum() {
        return thesisNum;
    }

    public void setThesisNum(Integer thesisNum) {
        this.thesisNum = thesisNum;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getRegUser() {
        return regUser;
    }

    public void setRegUser(Long regUser) {
        this.regUser = regUser;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getMeetStatus() {
        return meetStatus;
    }

    public void setMeetStatus(Integer meetStatus) {
        this.meetStatus = meetStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getJoinBegTime() {
        return joinBegTime;
    }

    public void setJoinBegTime(Date joinBegTime) {
        this.joinBegTime = joinBegTime;
    }

    public Date getJoinEndTime() {
        return joinEndTime;
    }

    public void setJoinEndTime(Date joinEndTime) {
        this.joinEndTime = joinEndTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getRealPeoNum() {
        return realPeoNum;
    }

    public void setRealPeoNum(Integer realPeoNum) {
        this.realPeoNum = realPeoNum;
    }

    public Integer getRealTheNum() {
        return realTheNum;
    }

    public void setRealTheNum(Integer realTheNum) {
        this.realTheNum = realTheNum;
    }

    @Override
    public String toString() {
        return "Meet{" +
        "meetId=" + meetId +
        ", meetName=" + meetName +
        ", meetDesc=" + meetDesc +
        ", place=" + place +
        ", peopleNum=" + peopleNum +
        ", thesisNum=" + thesisNum +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", regUser=" + regUser +
        ", regTime=" + regTime +
        "}";
    }

    public Integer getMustSub() {
        return mustSub;
    }

    public void setMustSub(Integer mustSub) {
        this.mustSub = mustSub;
    }
}
