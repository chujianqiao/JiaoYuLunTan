package cn.stylefeng.guns.modular.reviewdict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 评审字典表
 * </p>
 *
 * @author wucy
 * @since 2020-07-28
 */
@TableName("review_dictionary")
public class ReviewDictionary implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "dic_id", type = IdType.ID_WORKER)
    private Long dicId;

    /**
     * 字段名称
     */
    @TableField("dic_name")
    private String dicName;

    /**
     * 0-停用，1-启用
     */
    @TableField("dic_status")
    private Integer dicStatus;

    /**
     * 创建人ID
     */
      @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人ID
     */
      @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
      @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;


    public Long getDicId() {
        return dicId;
    }

    public void setDicId(Long dicId) {
        this.dicId = dicId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public Integer getDicStatus() {
        return dicStatus;
    }

    public void setDicStatus(Integer dicStatus) {
        this.dicStatus = dicStatus;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ReviewDictionary{" +
        "dicId=" + dicId +
        ", dicName=" + dicName +
        ", dicStatus=" + dicStatus +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
