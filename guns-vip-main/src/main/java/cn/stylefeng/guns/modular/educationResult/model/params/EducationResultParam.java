package cn.stylefeng.guns.modular.educationResult.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 优秀成果表
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-19
 */
@Data
public class EducationResultParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 成果ID
     */
    private Long resultId;

    /**
     * 成果名称
     */
    private String resultName;

    /**
     * 申请方式; 1-个人, 2-单位
     */
    private Integer applyType;

    /**
     * 负责人姓名
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
     * 负责人职称/职务
     */
    private String manaPost;

    /**
     * 负责人研究方向
     */
    private String manaDirect;

    /**
     * 成果意义
     */
    private String resultMean;

    /**
     * 应用价值
     */
    private String value;

    /**
     * 应用范围
     */
    private String resultRange;

    /**
     * 服务对象
     */
    private String object;

    /**
     * 攻关团队
     */
    private String team;

    /**
     * 研究结论与成果
     */
    private String conclusion;

    /**
     * 过程简介
     */
    private String introduce;

    /**
     * 成果影响力
     */
    private String influence;

    /**
     * 宣传口号
     */
    private String slogan;

    /**
     * 易拉宝设计图路径
     */
    private String designImg;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 专家推荐信附件路径
     */
    private String letterPath;

    /**
     * 原创承诺书路径
     */
    private String commitPath;

    /**
     * 成果形式
     */
    private String form;

    /**
     * 成果内容
     */
    private String detail;

    /**
     * 申报状态; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
     */
    private Integer checkStatus;

    /**
     * 申请人/单位ID
     */
    private Long applyId;

    /**
     * 申请提交时间
     */
    private Date applyTime;

    /**
     * 申请驳回时间
     */
    private Date refuseTime;

    /**
     * 审核通过时间
     */
    private Date passTime;

    /**
     * 取消申请时间
     */
    private Date cancelTime;

    /**
     * 归属单位/个人
     */
    private String belongName;

    /**
     * 专家推荐信附件名称
     */
    private String letterName;

    /**
     * 原创承诺书名称
     */
    private String commitName;

    /**
     * 优秀成果简介附件路径
     */
    private String introducePath;

    /**
     * 优秀成果简介附件名称
     */
    private String introduceName;

    @Override
    public String checkParam() {
        return null;
    }

}
