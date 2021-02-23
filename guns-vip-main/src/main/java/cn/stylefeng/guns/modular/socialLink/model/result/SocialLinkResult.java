package cn.stylefeng.guns.modular.socialLink.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 合作方式表
 * </p>
 *
 * @author CHU
 * @since 2020-07-15
 */
@Data
public class SocialLinkResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 环节ID
     */
    private Long linkId;

    /**
     * 环节名称
     */
    private String linkName;

    /**
     * 备注
     */
    private String description;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 排序
     */
    private Integer sort;

}
