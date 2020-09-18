package cn.stylefeng.guns.thesisReviewMiddle.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.thesisReviewMiddle.entity.ThesisReviewMiddle;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.model.result.ThesisReviewMiddleResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文专家中间表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-08-05
 */
public interface ThesisReviewMiddleService extends IService<ThesisReviewMiddle> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-08-05
     */
    void add(ThesisReviewMiddleParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-08-05
     */
    void delete(ThesisReviewMiddleParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-08-05
     */
    void update(ThesisReviewMiddleParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-05
     */
    ThesisReviewMiddleResult findBySpec(ThesisReviewMiddleParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-08-05
     */
    List<ThesisReviewMiddleResult> findListBySpec(ThesisReviewMiddleParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-05
     */
     LayuiPageInfo findPageBySpec(ThesisReviewMiddleParam param);

    List<ThesisReviewMiddleResult> getByThesisId(Long thesisId, Integer reviewBatch);

}
