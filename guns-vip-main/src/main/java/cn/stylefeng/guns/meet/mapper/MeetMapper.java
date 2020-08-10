package cn.stylefeng.guns.meet.mapper;

import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.model.result.MeetResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-08-05
 */
public interface MeetMapper extends BaseMapper<Meet> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-08-05
     */
    List<MeetResult> customList(@Param("paramCondition") MeetParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-08-05
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") MeetParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-08-05
     */
    Page<MeetResult> customPageList(@Param("page") Page page, @Param("paramCondition") MeetParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-08-05
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") MeetParam paramCondition);

}
