package cn.stylefeng.guns.modular.socialForum.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 社会资助论坛表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-15
 */
@Data
public class SocialForumParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 论坛ID
     */
    private Long forumId;

    /**
     * 论坛名称
     */
    private String forumName;

    /**
     * 论坛描述
     */
    private String forumDesc;

    /**
     * 申报状态; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
     */
    private Integer applyStatus;

    /**
     * 申报企业/单位名称
     */
    private String unitName;

    /**
     * 企业/单位所在地
     */
    private String unitPlace;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 负责人电话
     */
    private String manaPhone;

    /**
     * 负责人邮箱
     */
    private String manaEmail;

    /**
     * 已资助的会议
     */
    private String alreadyMeet;

    /**
     * 拟资助版块
     */
    private String supPlate;

    /**
     * 资助金额
     */
    private Integer supMoney;

    /**
     * 合同条件附件路径
     */
    private String contractPath;

    /**
     * 申报时间
     */
    private Date applyTime;

    /**
     * 申报单位ID
     */
    private Long applyId;

    /**
     * 合同条件附件名称
     */
    private String contractName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 统一社会信用代码
     */
    private String creditCode;
    private Long meetId;

    @Override
    public String checkParam() {
        return null;
    }

}
