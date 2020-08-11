package cn.stylefeng.guns.expert.mapper;

import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.model.result.ReviewMajorResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评审专家表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-05-11
 */
public interface ReviewMajorMapper extends BaseMapper<ReviewMajor> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-05-11
     */
    List<ReviewMajorResult> customList(@Param("paramCondition") ReviewMajorParam paramCondition);


    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-05-11
     */
    List<ReviewMajorResult> selectByDomain(@Param("paramCondition") ReviewMajorParam paramCondition);


    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-05-11
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ReviewMajorParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-05-11
     */
    Page<ReviewMajorResult> customPageList(@Param("page") Page page, @Param("paramCondition") ReviewMajorParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-05-11
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page,
												@Param("paramCondition") ReviewMajorParam paramCondition,
												@Param("userId") long userId,
												@Param("paramIds") String paramIds
	);

    /**
     * 修改用户状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    Integer setStatus(Long reviewId, String status);

    List<Map<String, Object>> majorMapList(String domain);
}
