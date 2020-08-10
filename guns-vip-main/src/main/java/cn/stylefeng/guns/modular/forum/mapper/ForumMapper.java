package cn.stylefeng.guns.modular.forum.mapper;

import cn.stylefeng.guns.modular.forum.entity.Forum;
import cn.stylefeng.guns.modular.forum.model.params.ForumParam;
import cn.stylefeng.guns.modular.forum.model.result.ForumResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分论坛表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-08-10
 */
public interface ForumMapper extends BaseMapper<Forum> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-08-10
     */
    List<ForumResult> customList(@Param("paramCondition") ForumParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-08-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ForumParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-08-10
     */
    Page<ForumResult> customPageList(@Param("page") Page page, @Param("paramCondition") ForumParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-08-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") ForumParam paramCondition);

    Integer setStatus(@Param("status") Long forumId, @Param("status") Integer status);
}
