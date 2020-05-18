package cn.stylefeng.guns.modular.educationResult.mapper;

import cn.stylefeng.guns.modular.educationResult.entity.EducationResult;
import cn.stylefeng.guns.modular.educationResult.model.params.EducationResultParam;
import cn.stylefeng.guns.modular.educationResult.model.result.EducationResultResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优秀成果表 Mapper 接口
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
public interface EducationResultMapper extends BaseMapper<EducationResult> {

    /**
     * 获取列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    List<EducationResultResult> customList(@Param("paramCondition") EducationResultParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EducationResultParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    Page<EducationResultResult> customPageList(@Param("page") Page page, @Param("paramCondition") EducationResultParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EducationResultParam paramCondition);

}
