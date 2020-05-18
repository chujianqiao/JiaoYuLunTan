package cn.stylefeng.guns.modular.greatResult.mapper;

import cn.stylefeng.guns.modular.greatResult.entity.GreatResult;
import cn.stylefeng.guns.modular.greatResult.model.params.GreatResultParam;
import cn.stylefeng.guns.modular.greatResult.model.result.GreatResultResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优秀成果表 Mapper 接口
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
public interface GreatResultMapper extends BaseMapper<GreatResult> {

    /**
     * 获取列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    List<GreatResultResult> customList(@Param("paramCondition") GreatResultParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") GreatResultParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    Page<GreatResultResult> customPageList(@Param("page") Page page, @Param("paramCondition") GreatResultParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") GreatResultParam paramCondition);

}
