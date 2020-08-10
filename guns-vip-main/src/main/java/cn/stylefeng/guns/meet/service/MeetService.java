package cn.stylefeng.guns.meet.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.model.result.MeetResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-08-05
 */
public interface MeetService extends IService<Meet> {

    /**
     * 新增
     *
     * @author wucy
     * @Date 2020-08-05
     */
    void add(MeetParam param);

    /**
     * 删除
     *
     * @author wucy
     * @Date 2020-08-05
     */
    void delete(MeetParam param);

    /**
     * 更新
     *
     * @author wucy
     * @Date 2020-08-05
     */
    void update(MeetParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author wucy
     * @Date 2020-08-05
     */
    MeetResult findBySpec(MeetParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author wucy
     * @Date 2020-08-05
     */
    List<MeetResult> findListBySpec(MeetParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author wucy
     * @Date 2020-08-05
     */
     LayuiPageInfo findPageBySpec(MeetParam param);

    /**
     * 查询数据（用于拼接字段）
     * @return
     */
    Page<Map<String, Object>> findPageWrap(MeetParam param);

}
