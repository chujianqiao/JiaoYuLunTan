package cn.stylefeng.guns.modular.material.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 会议材料表
 * </p>
 *
 * @author wucy
 * @since 2020-07-22
 */
@Data
public class MeetMaterialParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 材料ID
     */
    private Long materialId;

    /**
     * 材料路径
     */
    private String matPath;

    /**
     * 材料名称
     */
    private String matName;

    /**
     * 对应的会议ID
     */
    private Long meetId;

    @Override
    public String checkParam() {
        return null;
    }

}
