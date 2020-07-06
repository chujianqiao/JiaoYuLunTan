package cn.stylefeng.guns.domain.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class DomainParam implements Serializable, BaseValidatingParam {

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

    @Override
    public String checkParam() {
        return null;
    }

}
