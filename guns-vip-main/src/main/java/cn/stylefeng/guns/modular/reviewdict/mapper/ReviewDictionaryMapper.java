package cn.stylefeng.guns.modular.reviewdict.mapper;

import cn.stylefeng.guns.modular.reviewdict.entity.ReviewDictionary;
import cn.stylefeng.guns.modular.reviewdict.model.params.ReviewDictionaryParam;
import cn.stylefeng.guns.modular.reviewdict.model.result.ReviewDictionaryResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评审字典表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-07-28
 */
public interface ReviewDictionaryMapper extends BaseMapper<ReviewDictionary> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-07-28
     */
    List<ReviewDictionaryResult> customList(@Param("paramCondition") ReviewDictionaryParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-07-28
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ReviewDictionaryParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-07-28
     */
    Page<ReviewDictionaryResult> customPageList(@Param("page") Page page, @Param("paramCondition") ReviewDictionaryParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-07-28
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") ReviewDictionaryParam paramCondition);

}
