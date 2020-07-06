package cn.stylefeng.guns.domain.model.result;

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
 * @since 2020-07-06
 */
@Data
public class DomainResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long domainId;

    private Long pid;

    private String pids;

    private String domainName;

    private String description;

    private Integer version;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

}
