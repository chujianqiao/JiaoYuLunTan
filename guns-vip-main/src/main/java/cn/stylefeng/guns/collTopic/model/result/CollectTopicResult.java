package cn.stylefeng.guns.collTopic.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 论坛主题征集表
 * </p>
 *
 * @author wucy
 * @since 2020-05-18
 */
@Data
public class CollectTopicResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主题ID
     */
    private Long topicId;

    /**
     * 创建人ID
     */
    private Long createUser;

    /**
     * 大会主题
     */
    private String topicName;

    /**
     * 选题意义
     */
    private String topicDesc;

    /**
     * 分论坛主题
     */
    private String subTopic;

    /**
     * 票数
     */
    private Integer voteNum;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 管理员自定义备用字段1
     */
    private String diy1;

    /**
     * 管理员自定义备用字段2
     */
    private String diy2;

    private Long meetId;

}
