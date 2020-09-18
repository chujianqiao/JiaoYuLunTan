package cn.stylefeng.guns.modular.seat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 座位详情表
 * </p>
 *
 * @author wucy
 * @since 2020-09-11
 */
@TableName("seat_detail")
public class SeatDetail implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "seat_detail_id", type = IdType.ID_WORKER)
    private Long seatDetailId;

    /**
     * 座位列号
     */
    @TableField("seat_col")
    private Integer seatCol;

    /**
     * 座位行号
     */
    @TableField("seat_row")
    private Integer seatRow;

    /**
     * 会议ID/分论坛ID
     */
    @TableField("meet_id")
    private Long meetId;

    /**
     * 种类（1-会议/2-分论坛）
     */
    @TableField("meet_type")
    private Long meetType;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 单位名称
     */
    @TableField("unit_name")
    private String unitName;

    /**
     * 分配时间
     */
    @TableField("creat_time")
    private Date creatTime;


    public Long getSeatDetailId() {
        return seatDetailId;
    }

    public void setSeatDetailId(Long seatDetailId) {
        this.seatDetailId = seatDetailId;
    }

    public Integer getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(Integer seatCol) {
        this.seatCol = seatCol;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    public Long getMeetType() {
        return meetType;
    }

    public void setMeetType(Long meetType) {
        this.meetType = meetType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return "SeatDetail{" +
        "seatDetailId=" + seatDetailId +
        ", seatCol=" + seatCol +
        ", seatRow=" + seatRow +
        ", meetId=" + meetId +
        ", meetType=" + meetType +
        ", userId=" + userId +
        ", creatTime=" + creatTime +
        "}";
    }
}
