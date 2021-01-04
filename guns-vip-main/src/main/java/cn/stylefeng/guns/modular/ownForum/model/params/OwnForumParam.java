package cn.stylefeng.guns.modular.ownForum.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 自设论坛表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
@Data
public class OwnForumParam implements Serializable, BaseValidatingParam {

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
     * 申报种类; 1-个人,2-单位
     */
    private Integer applyType;

    /**
     * 申报状态; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请

     */
    private Integer applyStatus;

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
     * 分论坛ID,多个ID用逗号隔开
     */
    private String subId;

    /**
     * 是否有课题团队; 0-否,1-是
     */
    private Integer issubject;

    /**
     * 课题级别
     */
    private String subjectLev;

    /**
     * 课题名称
     */
    private String subjectName;

    /**
     * 论坛申报方案附件路径
     */
    private String planPath;

    /**
     * 与大会主题的关系
     */
    private String relation;

    /**
     * 选题意义
     */
    private String meaning;

    /**
     * 拟邀请专家姓名
     */
    private String expertName;

    /**
     * 参会人员类型
     */
    private String staffType;

    /**
     * 组织形式
     */
    private String orgType;

    /**
     * 人员分工表附件路径
     */
    private String dividePath;

    /**
     * 是否同意论坛章程;0-不同意,1-同意
     */
    private Integer agreeRule;

    /**
     * 申报人ID
     */
    private Long applyId;

    /**
     * 申报时间
     */
    private Date applyTime;

    /**
     * 人员分工表附件名称
     */
    private String divideName;

    /**
     * 申报方案附件名称
     */
    private String planName;

    /**
     * 论坛主题名称
     */
    private String forumTopic;

    /**
     * 论坛计划规模
     */
    private String forumSize;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 研究方向
     */
    private String direction;

    /**
     * 职称
     */
    private String title;

    /**
     * 职务
     */
    private String post;

    /**
     * 设置人数
     */
    private Integer setNum;

    /**
     * 已有人数
     */
    private Integer existNum;

    /**
     * 启用状态
     */
    private Integer status;

    /**
     * 报名开始时间
     */
    private Date startTime;

    /**
     * 报名结束时间
     */
    private Date endTime;

    private Long meetId;

    @Override
    public String checkParam() {
        return null;
    }

}
