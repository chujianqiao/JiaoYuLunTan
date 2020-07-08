package cn.stylefeng.guns.thesisDomain.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 论文领域表
 * </p>
 *
 * @author CHU
 * @since 2020-07-07
 */
@Data
public class ThesisDomainResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 领域ID
     */
    private Long domainId;

    /**
     * 父领域ID
     */
    private Long pid;

    /**
     * 父级ids
     */
    private String pids;

    /**
     * 领域名称
     */
    private String domainName;

    /**
     * 描述
     */
    private String description;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改人
     */
    private Long updateUser;

}
