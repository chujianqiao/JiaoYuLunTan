package cn.stylefeng.guns.modular.material.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 会议材料表
 * </p>
 *
 * @author wucy
 * @since 2020-07-22
 */
@TableName("meet_material")
public class MeetMaterial implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 材料ID
     */
      @TableId(value = "material_id", type = IdType.ID_WORKER)
    private Long materialId;

    /**
     * 材料路径
     */
    @TableField("mat_path")
    private String matPath;

    /**
     * 材料名称
     */
    @TableField("mat_name")
    private String matName;

    /**
     * 对应的会议ID
     */
    @TableField("meet_id")
    private Long meetId;


    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMatPath() {
        return matPath;
    }

    public void setMatPath(String matPath) {
        this.matPath = matPath;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    @Override
    public String toString() {
        return "MeetMaterial{" +
        "materialId=" + materialId +
        ", matPath=" + matPath +
        ", matName=" + matName +
        "}";
    }
}
