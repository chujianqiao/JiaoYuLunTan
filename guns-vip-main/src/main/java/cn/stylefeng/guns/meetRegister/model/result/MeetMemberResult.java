package cn.stylefeng.guns.meetRegister.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 会议注册成员表
 * </p>
 *
 * @author wucy
 * @since 2020-05-20
 */
@Data
public class MeetMemberResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long memberId;

    /**
     * 参会人员ID
     */
    private Long userId;

    /**
     * 参会论文ID
     */
    private Long thesisId;

    /**
     * 是否申请发言; 0-否,1-是
     */
    private Integer speak;

    /**
     * 是否同意参加形势研判会;0-否，1-是(只有教授可以选择)
     */
    private Integer judge;

    /**
     * 自设论坛ID
     */
    private Long ownForumid;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 会议状态；
     * 1-评审中,2-评审通过,3-已取消,4-已缴费
     */
    private Integer meetStatus;

    /**
     * 缴费记录ID
     */
    private Long payId;

    /**
     * 发言稿路径
     */
    private String wordPath;

    /**
     * 发言稿名称
     */
    private String wordName;

    /**
     * ppt路径
     */
    private String pptPath;

    /**
     * ppt名称
     */
    private String pptName;

}
