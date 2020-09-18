package cn.stylefeng.guns.modular.seat.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.seat.entity.SeatDetail;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatDetailResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 座位详情表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-09-11
 */
public interface SeatDetailService extends IService<SeatDetail> {

    /**
     * 新增
     * @author wucy
     * @Date 2020-09-11
     */
    void add(SeatDetailParam param);

    /**
     * 删除
     * @author wucy
     * @Date 2020-09-11
     */
    void delete(SeatDetailParam param);

    /**
     * 更新
     * @author wucy
     * @Date 2020-09-11
     */
    void update(SeatDetailParam param);

    /**
     * 查询单条数据，Specification模式
     * @author wucy
     * @Date 2020-09-11
     */
    SeatDetailResult findBySpec(SeatDetailParam param);

    /**
     * 查询列表，Specification模式
     * @author wucy
     * @Date 2020-09-11
     */
    List<SeatDetailResult> findListBySpec(SeatDetailParam param);

    /**
     * 查询分页数据，Specification模式
     * @author wucy
     * @Date 2020-09-11
     */
     LayuiPageInfo findPageBySpec(SeatDetailParam param);

    /**
     * 查询（wrap）
     * @param param
     * @return
     */
    Page<Map<String, Object>> findPageWrap(SeatDetailParam param);

    /**
     * 删除数据
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int deleteData(SeatDetailParam param);

}
