package cn.stylefeng.guns.meet.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 会议表
 * </p>
 *
 * @author wucy
 * @since 2020-08-05
 */
@Data
public class MeetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 会议ID
     */
    private Long meetId;

    /**
     * 会议名称
     */
    private String meetName;

    /**
     * 会议描述
     */
    private String meetDesc;

    /**
     * 会议地点
     */
    private String place;

    /**
     * 参会人数限制
     */
    private Integer peopleNum;

    /**
     * 投稿人数限制
     */
    private Integer thesisNum;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 报名开始时间
     */
    private Date joinBegTime;

    /**
     * 报名结束时间
     */
    private Date joinEndTime;

    /**
     * 注册人
     */
    private Long regUser;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 会议状态
     * 0-未发布，1-已发布
     */
    private Integer meetStatus;

    /**
     * 富文本编辑器的内容
     */
    private String content;

    /**
     * 报名费
     */
    private BigDecimal fee;

    /**
     * 大小会（大会-big,小会-small）
     */
    private String size;

    /**
     * 实际参会人数
     */
    private Integer realPeoNum;

    /**
     * 实际投稿人数
     */
    private Integer realTheNum;

    @Override
    public String checkParam() {
        return null;
    }

}
