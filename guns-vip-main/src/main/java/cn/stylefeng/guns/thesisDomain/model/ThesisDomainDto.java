package cn.stylefeng.guns.thesisDomain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ThesisDomainDto implements Serializable {

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
     * 父领域名称
     */
    private String pName;

    /**
     * 所属专家名称
     */
    private String belongMajor;

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
