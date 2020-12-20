package cn.stylefeng.guns.modular.checkIn.mapper;

import cn.stylefeng.guns.modular.checkIn.entity.CheckIn;
import cn.stylefeng.guns.modular.checkIn.model.params.CheckInParam;
import cn.stylefeng.guns.modular.checkIn.model.result.CheckInResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报到签到表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-07-30
 */
public interface CheckInMapper extends BaseMapper<CheckIn> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    List<CheckInResult> customList(@Param("paramCondition") CheckInParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") CheckInParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    Page<CheckInResult> customPageList(@Param("page") Page page, @Param("paramCondition") CheckInParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") CheckInParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    Page<Map<String, Object>> customPageMapListByRole(@Param("page") Page page, @Param("paramCondition") CheckInParam paramCondition, @Param("userIds") List<Long> userIds);


    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    Page<Map<String, Object>> customPageMapListForum(@Param("page") Page page, @Param("paramCondition") CheckInParam paramCondition);
    Page<Map<String, Object>> customPageMapListForumByRole(@Param("page") Page page, @Param("paramCondition") CheckInParam paramCondition, @Param("userIds") List<Long> userIds);

    List<CheckIn> getByUser(@Param("userId") Long userId,@Param("meetId") Long meetId,@Param("forumId") Long forumId);

}
