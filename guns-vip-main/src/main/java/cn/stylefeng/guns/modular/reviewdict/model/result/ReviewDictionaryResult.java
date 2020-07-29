package cn.stylefeng.guns.modular.reviewdict.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 评审字典表
 * </p>
 *
 * @author wucy
 * @since 2020-07-28
 */
@Data
public class ReviewDictionaryResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键ID
     */
    private Long dicId;

    /**
     * 字段名称
     */
    private String dicName;

    /**
     * 0-停用，1-启用
     */
    private Integer dicStatus;

    /**
     * 创建人ID
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人ID
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

}
