package cn.stylefeng.guns.reviewunit.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.reviewunit.entity.ReviewUnit;
import cn.stylefeng.guns.reviewunit.model.params.ReviewUnitParam;
import cn.stylefeng.guns.reviewunit.model.result.ReviewUnitResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 理事单位表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-05-14
 */
public interface ReviewUnitService extends IService<ReviewUnit> {

    /**
     * 新增
     * @author wucy
     * @Date 2020-05-14
     */
    void add(ReviewUnitParam param);

    /**
     * 删除
     * @author wucy
     * @Date 2020-05-14
     */
    void delete(ReviewUnitParam param);

    /**
     * 更新
     * @author wucy
     * @Date 2020-05-14
     */
    void update(ReviewUnitParam param);

    /**
     * 查询单条数据，Specification模式
     * @author wucy
     * @Date 2020-05-14
     */
    ReviewUnitResult findBySpec(ReviewUnitParam param);

    /**
     * 查询列表，Specification模式
     * @author wucy
     * @Date 2020-05-14
     */
    List<ReviewUnitResult> findListBySpec(ReviewUnitParam param);

    /**
     * 查询分页数据，Specification模式
     * @author wucy
     * @Date 2020-05-14
     */
     LayuiPageInfo findPageBySpec(ReviewUnitParam param);

    /**
     * 查询数据（用于拼接字段）
     * @return
     */
    Page<Map<String, Object>> findPageWrap(ReviewUnitParam param);

}
