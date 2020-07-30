package cn.stylefeng.guns.thesis.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 论文表
 * </p>
 *
 * @author wucy
 * @since 2020-05-21
 */
@Data
public class ThesisParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 论文ID
     */
    private Long thesisId;

    /**
     * 论文题目
     */
    private String thesisTitle;

    /**
     * 英文题目
     */
    private String engTitle;

    /**
     * 论文作者ID
     */
    private String thesisUser;

    /**
     * 评审字典项
     */
    private String status;

    /**
     * 评审结果; 0-不通过，1-通过
     */
    private Integer reviewResult;

    /**
     * 是否推荐优秀; 0-否，1-是
     */
    private Integer isgreat;

    /**
     * 推荐专家人数
     */
    private Integer greatNum;

    /**
     * 推优专家ID,多个用逗号隔开
     */
    private String greatId;

    /**
     * 论文提交时间
     */
    private Date applyTime;

    /**
     * 正文
     */
    private String thesisText;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 评审人ID,多个用逗号隔开
     */
    private String reviewUser;

    /**
     * 是否为优秀论文;0-否,1-是
     */
    private Integer great;

    /**
     * 中文关键词
     */
    private String cnKeyword;

    /**
     * 英文关键词
     */
    private String engKeyword;

    /**
     * 中文摘要
     */
    private String cnAbstract;

    /**
     * 英文摘要
     */
    private String engAbstract;

    /**
     * 参会论文研究方向
     */
    private String thesisDirect;

    /**
     * 论文附件路径
     */
    private String thesisPath;

    /**
     * 论文附件文件名
     */
    private String fileName;

    /**
     * 所属领域
     */
    private String belongDomain;

    @Override
    public String checkParam() {
        return null;
    }

}
