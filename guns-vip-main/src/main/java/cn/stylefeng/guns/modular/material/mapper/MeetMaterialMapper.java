package cn.stylefeng.guns.modular.material.mapper;

import cn.stylefeng.guns.modular.material.entity.MeetMaterial;
import cn.stylefeng.guns.modular.material.model.params.MeetMaterialParam;
import cn.stylefeng.guns.modular.material.model.result.MeetMaterialResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议材料表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-07-22
 */
public interface MeetMaterialMapper extends BaseMapper<MeetMaterial> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-07-22
     */
    List<MeetMaterialResult> customList(@Param("paramCondition") MeetMaterialParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-07-22
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") MeetMaterialParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-07-22
     */
    Page<MeetMaterialResult> customPageList(@Param("page") Page page, @Param("paramCondition") MeetMaterialParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-07-22
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") MeetMaterialParam paramCondition);

}
