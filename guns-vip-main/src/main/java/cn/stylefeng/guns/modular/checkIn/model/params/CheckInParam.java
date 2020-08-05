package cn.stylefeng.guns.modular.checkIn.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 报到签到表
 * </p>
 *
 * @author CHU
 * @since 2020-07-30
 */
@Data
public class CheckInParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 报到签到ID
     */
    private Long checkId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 报到状态 0-未报到 1-已报到
     */
    private Integer registerStatus;

    /**
     * 报到时间
     */
    private Date registerTime;

    /**
     * 签到状态 0-未报到 1-已报到
     */
    private Integer signStatus;

    /**
     * 签到时间
     */
    private Date signTime;

    /**
     * 签到地点
     */
    private String signPlace;

    @Override
    public String checkParam() {
        return null;
    }

}
