package cn.stylefeng.guns.modular.bill.mapper;

import cn.stylefeng.guns.modular.bill.entity.Bill;
import cn.stylefeng.guns.modular.bill.model.params.BillParam;
import cn.stylefeng.guns.modular.bill.model.result.BillResult;
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
 * @author CHU
 * @since 2020-11-05
 */
public interface BillMapper extends BaseMapper<Bill> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-11-05
     */
    List<BillResult> customList(@Param("paramCondition") BillParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-11-05
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") BillParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-11-05
     */
    Page<BillResult> customPageList(@Param("page") Page page, @Param("paramCondition") BillParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-11-05
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") BillParam paramCondition);


    Bill findByUserAndMeetMember(@Param("userId") Long userId, @Param("meetMemberId") Long meetMemberId);

    Page<Map<String, Object>> customPageMap(@Param("page") Page page, @Param("paramCondition") BillParam paramCondition, @Param("userIds") List<Long> userIds);
}
