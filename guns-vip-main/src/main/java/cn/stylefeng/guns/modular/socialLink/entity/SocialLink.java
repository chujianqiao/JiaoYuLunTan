package cn.stylefeng.guns.modular.socialLink.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 合作方式表
 * </p>
 *
 * @author CHU
 * @since 2020-07-15
 */
@TableName("social_link")
public class SocialLink implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 环节ID
     */
      @TableId(value = "link_id", type = IdType.ID_WORKER)
    private Long linkId;

    /**
     * 环节名称
     */
    @TableField("link_name")
    private String linkName;

    /**
     * 备注
     */
    @TableField("description")
    private String description;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;


    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SocialLink{" +
        "linkId=" + linkId +
        ", linkName=" + linkName +
        ", description=" + description +
        ", status=" + status +
        ", createTime=" + createTime +
        ", sort=" + sort +
        "}";
    }
}
