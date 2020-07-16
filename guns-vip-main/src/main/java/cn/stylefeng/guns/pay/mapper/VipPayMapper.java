package cn.stylefeng.guns.pay.mapper;

import cn.stylefeng.guns.pay.entity.VipPay;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.model.result.VipPayResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员缴费表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-07-15
 */
public interface VipPayMapper extends BaseMapper<VipPay> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-07-15
     */
    List<VipPayResult> customList(@Param("paramCondition") VipPayParam paramCondition);

    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-07-15
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") VipPayParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-07-15
     */
    Page<VipPayResult> customPageList(@Param("page") Page page, @Param("paramCondition") VipPayParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-07-15
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") VipPayParam paramCondition);

}
