package cn.stylefeng.guns.modular.seat.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.model.params.SeatParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wucy
 * @since 2020-09-10
 */
public interface SeatService extends IService<Seat> {

    /**
     * 新增
     * @author wucy
     * @Date 2020-09-10
     */
    void add(SeatParam param);

    /**
     * 删除
     * @author wucy
     * @Date 2020-09-10
     */
    void delete(SeatParam param);

    /**
     * 更新
     * @author wucy
     * @Date 2020-09-10
     */
    void update(SeatParam param);

    /**
     * 查询单条数据，Specification模式
     * @author wucy
     * @Date 2020-09-10
     */
    SeatResult findBySpec(SeatParam param);

    /**
     * 查询列表，Specification模式
     * @author wucy
     * @Date 2020-09-10
     */
    List<SeatResult> findListBySpec(SeatParam param);

    /**
     * 查询分页数据，Specification模式
     * @author wucy
     * @Date 2020-09-10
     */
     LayuiPageInfo findPageBySpec(SeatParam param);

    /**
     * 查询数据（用于拼接字段）
     * @return
     */
    Page<Map<String, Object>> findPageWrap(SeatParam param);

}
