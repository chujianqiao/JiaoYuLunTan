package cn.stylefeng.guns.modular.seat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 座次基本信息
 * </p>
 *
 * @author wucy
 * @since 2020-09-10
 */
@TableName("seat")
public class Seat implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "seat_id", type = IdType.ID_WORKER)
    private Long seatId;

    /**
     * 对应的会议ID或分论坛ID
     */
    @TableField("meet_id")
    private Long meetId;

    /**
     * 种类（1-会议/2-分论坛）
     */
    @TableField("meet_type")
    private Long meetType;

    /**
     * 列数
     */
    @TableField("col_num")
    private Integer colNum;

    /**
     * 行数
     */
    @TableField("row_num")
    private Integer rowNum;

    /**
     * 排列方式（A-正常,B-奇偶）
     */
    @TableField("seat_type")
    private String seatType;


    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
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

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    @Override
    public String toString() {
        return "Seat{" +
        "seatId=" + seatId +
        ", meetId=" + meetId +
        ", meetType=" + meetType +
        ", colNum=" + colNum +
        ", rowNum=" + rowNum +
        ", seatType=" + seatType +
        "}";
    }
}
