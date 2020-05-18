package cn.stylefeng.guns.modular.holdforum.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 承办方论坛表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-14
 */
@Data
public class HoldForumParam implements Serializable, BaseValidatingParam {

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
     * 单位名称
     */
    private String unitName;

    /**
     * 单位简介
     */
    private String unitDesc;

    /**
     * 级别
     */
    private String level;

    /**
     * 承办年份
     */
    private String year;

    /**
     * 承办能力(会议规模)
     */
    private String ability;

    /**
     * 论坛主题
     */
    private String topic;

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
     * 组织保障
     */
    private String orgSup;

    /**
     * 经费保障
     */
    private String fundsSup;

    /**
     * 人员保障
     */
    private String staffSup;

    /**
     * 办会经验
     */
    private String experience;

    /**
     * 是否同意论坛章程; 0-不同意,1-同意
     */
    private Integer agreeRule;

    /**
     * 承办方案附件路径
     */
    private String planPath;

    /**
     * 承诺书路径
     */
    private String commitPath;

    /**
     * 申报时间
     */
    private Date applyTime;

    /**
     * 申报人ID
     */
    private Long applyUser;

    /**
     * 承办方案附件名称
     */
    private String planName;

    /**
     * 承诺书附件名称
     */
    private String commitName;

    @Override
    public String checkParam() {
        return null;
    }

}
