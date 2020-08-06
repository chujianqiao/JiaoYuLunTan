package cn.stylefeng.guns.thesisReviewMiddle.mapper;

import cn.stylefeng.guns.thesisReviewMiddle.entity.ThesisReviewMiddle;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.model.result.ThesisReviewMiddleResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文专家中间表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-08-05
 */
public interface ThesisReviewMiddleMapper extends BaseMapper<ThesisReviewMiddle> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-08-05
     */
    List<ThesisReviewMiddleResult> customList(@Param("paramCondition") ThesisReviewMiddleParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-08-05
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ThesisReviewMiddleParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-08-05
     */
    Page<ThesisReviewMiddleResult> customPageList(@Param("page") Page page, @Param("paramCondition") ThesisReviewMiddleParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-08-05
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") ThesisReviewMiddleParam paramCondition);

}
