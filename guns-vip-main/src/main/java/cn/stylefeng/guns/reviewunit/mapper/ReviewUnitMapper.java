package cn.stylefeng.guns.reviewunit.mapper;

import cn.stylefeng.guns.reviewunit.entity.ReviewUnit;
import cn.stylefeng.guns.reviewunit.model.params.ReviewUnitParam;
import cn.stylefeng.guns.reviewunit.model.result.ReviewUnitResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 理事单位表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-05-14
 */
public interface ReviewUnitMapper extends BaseMapper<ReviewUnit> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-05-14
     */
    List<ReviewUnitResult> customList(@Param("paramCondition") ReviewUnitParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-05-14
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ReviewUnitParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-05-14
     */
    Page<ReviewUnitResult> customPageList(@Param("page") Page page,
                                          @Param("paramCondition") ReviewUnitParam paramCondition,
                                          @Param("userId") long userId);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-05-14
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page,
                                                @Param("paramCondition") ReviewUnitParam paramCondition,
                                                @Param("paramIds") String paramIds);

}
