package cn.stylefeng.guns.modular.seat.mapper;

import cn.stylefeng.guns.modular.seat.entity.SeatDetail;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatDetailResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 座位详情表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-09-11
 */
public interface SeatDetailMapper extends BaseMapper<SeatDetail> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-09-11
     */
    List<SeatDetailResult> customList(@Param("paramCondition") SeatDetailParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-09-11
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SeatDetailParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-09-11
     */
    Page<SeatDetailResult> customPageList(@Param("page") Page page, @Param("paramCondition") SeatDetailParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-09-11
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SeatDetailParam paramCondition);


    /**
     * 删除数据
     * @param paramCondition
     * @return
     */
    int deleteData(@Param("paramCondition") SeatDetailParam paramCondition);
}
