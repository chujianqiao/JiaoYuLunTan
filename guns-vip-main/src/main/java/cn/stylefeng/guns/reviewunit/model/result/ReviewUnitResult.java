package cn.stylefeng.guns.reviewunit.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 理事单位表
 * </p>
 *
 * @author wucy
 * @since 2020-05-14
 */
@Data
public class ReviewUnitResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 单位ID
     */
    private Long reviewId;

    /**
     * 单位所在地
     */
    private String location;

    /**
     * 担任理事单位年份
     */
    private String year;

    /**
     * 代表姓名
     */
    private String repName;

    /**
     * 代表职称/职务
     */
    private String post;

    /**
     * 学历
     */
    private String education;

    /**
     * 导入时间
     */
    private Date createTime;

}
