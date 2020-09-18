package cn.stylefeng.guns.modular.seat.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 座位详情表
 * </p>
 *
 * @author wucy
 * @since 2020-09-11
 */
@Data
public class SeatDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long seatDetailId;

    /**
     * 座位列号
     */
    private Integer seatCol;

    /**
     * 座位行号
     */
    private Integer seatRow;

    /**
     * 会议ID/分论坛ID
     */
    private Long meetId;

    /**
     * 种类（1-会议/2-分论坛）
     */
    private Long meetType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 分配时间
     */
    private Date creatTime;

    /**
     * 单位名称
     */
    private String unitName;

    @Override
    public String checkParam() {
        return null;
    }

}
