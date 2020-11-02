package cn.stylefeng.guns.modular.checkIn.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.checkIn.entity.CheckIn;
import cn.stylefeng.guns.modular.checkIn.model.params.CheckInParam;
import cn.stylefeng.guns.modular.checkIn.model.result.CheckInResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报到签到表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-07-30
 */
public interface CheckInService extends IService<CheckIn> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-07-30
     */
    void add(CheckInParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-07-30
     */
    void delete(CheckInParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-07-30
     */
    void update(CheckInParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-30
     */
    CheckInResult findBySpec(CheckInParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-07-30
     */
    List<CheckInResult> findListBySpec(CheckInParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-30
     */
     LayuiPageInfo findPageBySpec(CheckInParam param);

    /**
     * 拼接字段用
     * @param param
     * @return
     */
    Page<Map<String, Object>> findPageWrap(CheckInParam param);
    /**
     * 拼接字段用
     * @param param
     * @return
     */
    Page<Map<String, Object>> findPageWrap(CheckInParam param, List<Long> userIds);
    /**
     * 拼接字段用
     * @param param
     * @return
     */
    Page<Map<String, Object>> findPageWrapForum(CheckInParam param);
    Page<Map<String, Object>> findPageWrapForum(CheckInParam param, List<Long> userIds);

    CheckIn getByUser(Long userId);
}
