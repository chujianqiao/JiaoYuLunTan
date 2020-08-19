package cn.stylefeng.guns.modular.greatReviewMiddle.mapper;

import cn.stylefeng.guns.modular.greatReviewMiddle.entity.GreatReviewMiddle;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.params.GreatReviewMiddleParam;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.result.GreatReviewMiddleResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 著作专家中间表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
public interface GreatReviewMiddleMapper extends BaseMapper<GreatReviewMiddle> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    List<GreatReviewMiddleResult> customList(@Param("paramCondition") GreatReviewMiddleParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") GreatReviewMiddleParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    Page<GreatReviewMiddleResult> customPageList(@Param("page") Page page, @Param("paramCondition") GreatReviewMiddleParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") GreatReviewMiddleParam paramCondition);

}
