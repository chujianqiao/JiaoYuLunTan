package cn.stylefeng.guns.collTopic.mapper;

import cn.stylefeng.guns.collTopic.entity.CollectTopic;
import cn.stylefeng.guns.collTopic.model.params.CollectTopicParam;
import cn.stylefeng.guns.collTopic.model.result.CollectTopicResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论坛主题征集表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-05-18
 */
public interface CollectTopicMapper extends BaseMapper<CollectTopic> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-05-18
     */
    List<CollectTopicResult> customList(@Param("paramCondition") CollectTopicParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-05-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") CollectTopicParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-05-18
     */
    Page<CollectTopicResult> customPageList(@Param("page") Page page, @Param("paramCondition") CollectTopicParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-05-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") CollectTopicParam paramCondition);

}
