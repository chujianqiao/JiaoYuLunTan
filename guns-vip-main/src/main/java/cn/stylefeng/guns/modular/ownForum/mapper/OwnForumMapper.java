package cn.stylefeng.guns.modular.ownForum.mapper;

import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.model.params.OwnForumParam;
import cn.stylefeng.guns.modular.ownForum.model.result.OwnForumResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自设论坛表 Mapper 接口
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
public interface OwnForumMapper extends BaseMapper<OwnForum> {

    /**
     * 获取列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    List<OwnForumResult> customList(@Param("paramCondition") OwnForumParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") OwnForumParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    Page<OwnForumResult> customPageList(@Param("page") Page page, @Param("paramCondition") OwnForumParam paramCondition);

    /**
     * 获取admin分页实体列表
     *
     * @author
     * @Date 2020-05-13
     */
    Page<OwnForumResult> customPageListAdmin(@Param("page") Page page, @Param("paramCondition") OwnForumParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") OwnForumParam paramCondition);
    Page<Map<String, Object>> customPageMapListAdmin(@Param("page") Page page, @Param("paramCondition") OwnForumParam paramCondition);

}
