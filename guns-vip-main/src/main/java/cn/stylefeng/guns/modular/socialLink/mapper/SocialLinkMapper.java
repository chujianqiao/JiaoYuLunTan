package cn.stylefeng.guns.modular.socialLink.mapper;

import cn.stylefeng.guns.modular.socialLink.entity.SocialLink;
import cn.stylefeng.guns.modular.socialLink.model.params.SocialLinkParam;
import cn.stylefeng.guns.modular.socialLink.model.result.SocialLinkResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 赞助环节表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-07-15
 */
public interface SocialLinkMapper extends BaseMapper<SocialLink> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-07-15
     */
    List<SocialLinkResult> customList(@Param("paramCondition") SocialLinkParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-07-15
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SocialLinkParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-07-15
     */
    Page<SocialLinkResult> customPageList(@Param("page") Page page, @Param("paramCondition") SocialLinkParam paramCondition);

    Page<SocialLinkResult> customPageListAll(@Param("page") Page page, @Param("paramCondition") SocialLinkParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-07-15
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SocialLinkParam paramCondition);

    Integer setStatus(@Param("linkId") Long linkId, @Param("status") String status);
}
