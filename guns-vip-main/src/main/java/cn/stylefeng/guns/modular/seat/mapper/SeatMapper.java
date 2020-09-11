package cn.stylefeng.guns.modular.seat.mapper;

import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.model.params.SeatParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-09-10
 */
public interface SeatMapper extends BaseMapper<Seat> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-09-10
     */
    List<SeatResult> customList(@Param("paramCondition") SeatParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-09-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SeatParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-09-10
     */
    Page<SeatResult> customPageList(@Param("page") Page page, @Param("paramCondition") SeatParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SeatParam paramCondition);

}
