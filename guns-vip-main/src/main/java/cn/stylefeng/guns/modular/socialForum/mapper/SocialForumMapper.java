package cn.stylefeng.guns.modular.socialForum.mapper;

import cn.stylefeng.guns.modular.socialForum.entity.SocialForum;
import cn.stylefeng.guns.modular.socialForum.model.params.SocialForumParam;
import cn.stylefeng.guns.modular.socialForum.model.result.SocialForumResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社会资助论坛表 Mapper 接口
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-15
 */
public interface SocialForumMapper extends BaseMapper<SocialForum> {

    /**
     * 获取列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    List<SocialForumResult> customList(@Param("paramCondition") SocialForumParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SocialForumParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    Page<SocialForumResult> customPageList(@Param("page") Page page, @Param("paramCondition") SocialForumParam paramCondition);
    /**
     * 获取admin分页实体列表
     *
     * @author
     * @Date 2020-05-13
     */
    Page<SocialForumResult> customPageListAdmin(@Param("page") Page page, @Param("paramCondition") SocialForumParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SocialForumParam paramCondition);

}
