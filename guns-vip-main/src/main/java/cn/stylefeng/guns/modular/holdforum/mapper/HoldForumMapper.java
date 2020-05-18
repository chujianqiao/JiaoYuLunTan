package cn.stylefeng.guns.modular.holdforum.mapper;

import cn.stylefeng.guns.modular.holdforum.entity.HoldForum;
import cn.stylefeng.guns.modular.holdforum.model.params.HoldForumParam;
import cn.stylefeng.guns.modular.holdforum.model.result.HoldForumResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 承办方论坛表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-05-13
 */
public interface HoldForumMapper extends BaseMapper<HoldForum> {

    /**
     * 获取列表
     *
     * @author 
     * @Date 2020-05-13
     */
    List<HoldForumResult> customList(@Param("paramCondition") HoldForumParam paramCondition);

    /**
     * 获取map列表
     *
     * @author 
     * @Date 2020-05-13
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") HoldForumParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author 
     * @Date 2020-05-13
     */
    Page<HoldForumResult> customPageList(@Param("page") Page page, @Param("paramCondition") HoldForumParam paramCondition);
    /**
     * 获取admin分页实体列表
     *
     * @author
     * @Date 2020-05-13
     */
    Page<HoldForumResult> customPageListAdmin(@Param("page") Page page, @Param("paramCondition") HoldForumParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author 
     * @Date 2020-05-13
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") HoldForumParam paramCondition);

}
