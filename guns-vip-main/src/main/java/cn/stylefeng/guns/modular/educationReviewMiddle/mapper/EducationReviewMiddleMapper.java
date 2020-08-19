package cn.stylefeng.guns.modular.educationReviewMiddle.mapper;

import cn.stylefeng.guns.modular.educationReviewMiddle.entity.EducationReviewMiddle;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.params.EducationReviewMiddleParam;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.result.EducationReviewMiddleResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教改成果专家中间表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
public interface EducationReviewMiddleMapper extends BaseMapper<EducationReviewMiddle> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    List<EducationReviewMiddleResult> customList(@Param("paramCondition") EducationReviewMiddleParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EducationReviewMiddleParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    Page<EducationReviewMiddleResult> customPageList(@Param("page") Page page, @Param("paramCondition") EducationReviewMiddleParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EducationReviewMiddleParam paramCondition);

}
