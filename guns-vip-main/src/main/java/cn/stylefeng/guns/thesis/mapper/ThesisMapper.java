package cn.stylefeng.guns.thesis.mapper;

import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.model.result.ThesisResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-05-21
 */
public interface ThesisMapper extends BaseMapper<Thesis> {

    /**
     * 获取列表
     * @author wucy
     * @Date 2020-05-21
     */
    List<ThesisResult> customList(@Param("paramCondition") ThesisParam paramCondition);

    /**
     * 获取map列表
     * @author wucy
     * @Date 2020-05-21
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ThesisParam paramCondition);

    /**
     * 获取分页实体列表
     * @author wucy
     * @Date 2020-05-21
     */
    Page<ThesisResult> customPageList(@Param("page") Page page, @Param("paramCondition") ThesisParam paramCondition);

    /**
     * 获取分页map列表
     * @author wucy
     * @Date 2020-05-21
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") ThesisParam paramCondition);
    Page<Map<String, Object>> customPageMapListFirst(@Param("page") Page page, @Param("paramCondition") ThesisParam paramCondition);
    Page<Map<String, Object>> customPageMapListAgain(@Param("page") Page page, @Param("paramCondition") ThesisParam paramCondition);

    /**
     * 评审专家查询列表
     * @return
     */
    Page<Map<String, Object>> customReview(@Param("page") Page page, List<Long> list);

}
