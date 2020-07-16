package cn.stylefeng.guns.meetRegister.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会议注册成员表
 * </p>
 *
 * @author wucy
 * @since 2020-05-20
 */
@Data
public class MeetMemberParam implements Serializable, BaseValidatingParam {

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

    @Override
    public String checkParam() {
        return null;
    }

}
