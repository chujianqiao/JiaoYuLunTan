package cn.stylefeng.guns.modular.bill.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.bill.entity.Bill;
import cn.stylefeng.guns.modular.bill.model.params.BillParam;
import cn.stylefeng.guns.modular.bill.model.result.BillResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CHU
 * @since 2020-11-05
 */
public interface BillService extends IService<Bill> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-11-05
     */
    void add(BillParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-11-05
     */
    void delete(BillParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-11-05
     */
    void update(BillParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-11-05
     */
    BillResult findBySpec(BillParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-11-05
     */
    List<BillResult> findListBySpec(BillParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-11-05
     */
     LayuiPageInfo findPageBySpec(BillParam param);

    Bill findByUserAndMeetMember(Long userId, Long meetMemberId);

    Page<Map<String, Object>> findPageWrap(BillParam param, List<Long> userIds);

}
