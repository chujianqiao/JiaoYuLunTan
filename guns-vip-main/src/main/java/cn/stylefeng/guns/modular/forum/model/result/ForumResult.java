package cn.stylefeng.guns.modular.forum.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 分论坛表
 * </p>
 *
 * @author CHU
 * @since 2020-08-10
 */
@Data
public class ForumResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 分论坛ID
     */
    private Long forumId;

    /**
     * 分论坛名称
     */
    private String forumName;

    /**
     * 设置人数
     */
    private Integer setNum;

    /**
     * 已有人数
     */
    private Integer existNum;

    /**
     * 发布状态 0-未发布 1-已发布
     */
    private Integer status;

    /**
     * 注册开始时间
     */
    private Date registerStartTime;

    /**
     * 注册结束时间
     */
    private Date registerEndTime;

    /**
     * 参会开始时间
     */
    private Date startTime;

    /**
     * 参会结束时间
     */
    private Date endTime;

    /**
     * 论坛地点
     */
    private String location;

}
