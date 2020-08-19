package cn.stylefeng.guns.modular.greatReviewMiddle.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.greatReviewMiddle.entity.GreatReviewMiddle;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.params.GreatReviewMiddleParam;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.result.GreatReviewMiddleResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 著作专家中间表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
public interface GreatReviewMiddleService extends IService<GreatReviewMiddle> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-08-18
     */
    void add(GreatReviewMiddleParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-08-18
     */
    void delete(GreatReviewMiddleParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-08-18
     */
    void update(GreatReviewMiddleParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-18
     */
    GreatReviewMiddleResult findBySpec(GreatReviewMiddleParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-08-18
     */
    List<GreatReviewMiddleResult> findListBySpec(GreatReviewMiddleParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-18
     */
     LayuiPageInfo findPageBySpec(GreatReviewMiddleParam param);

}
