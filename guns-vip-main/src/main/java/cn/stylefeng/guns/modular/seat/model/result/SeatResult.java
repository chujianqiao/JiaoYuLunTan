package cn.stylefeng.guns.modular.seat.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author wucy
 * @since 2020-09-10
 */
@Data
public class SeatResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long seatId;

    /**
     * 对应的会议ID或分论坛ID
     */
    private Long meetId;

    /**
     * 种类（1-会议/2-分论坛）
     */
    private Long meetType;

    /**
     * 列数
     */
    private Integer colNum;

    /**
     * 行数
     */
    private Integer rowNum;

    /**
     * 排列方式（A-正常,B-奇偶）
     */
    private String seatType;

    /**
     * 主席台列数
     */
    private Integer platNum;

}
