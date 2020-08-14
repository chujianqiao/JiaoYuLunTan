package cn.stylefeng.guns.pay.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.pay.entity.VipPay;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.model.result.VipPayResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员缴费表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-07-15
 */
public interface VipPayService extends IService<VipPay> {

    /**
     * 新增
     *
     * @author wucy
     * @Date 2020-07-15
     */
    void add(VipPayParam param);

    /**
     * 删除
     *
     * @author wucy
     * @Date 2020-07-15
     */
    void delete(VipPayParam param);

    /**
     * 更新
     *
     * @author wucy
     * @Date 2020-07-15
     */
    void update(VipPayParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author wucy
     * @Date 2020-07-15
     */
    VipPayResult findBySpec(VipPayParam param);

    /**
     * 有条件的查询
     * @param paramCondition
     * @return
     */
    List<VipPayResult> customList(@Param("paramCondition") VipPayParam paramCondition);

    /**
     * 查询列表，Specification模式
     *
     * @author wucy
     * @Date 2020-07-15
     */
    List<VipPayResult> findListBySpec(VipPayParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author wucy
     * @Date 2020-07-15
     */
     LayuiPageInfo findPageBySpec(VipPayParam param);

    /**
     * 查询数据（用于拼接字段）
     * @return
     */
    Page<Map<String, Object>> findPageWrap(VipPayParam vipPayParam);

}
