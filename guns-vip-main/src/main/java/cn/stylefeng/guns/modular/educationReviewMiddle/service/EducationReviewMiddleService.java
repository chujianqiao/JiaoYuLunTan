package cn.stylefeng.guns.modular.educationReviewMiddle.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.educationReviewMiddle.entity.EducationReviewMiddle;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.params.EducationReviewMiddleParam;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.result.EducationReviewMiddleResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 教改成果专家中间表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
public interface EducationReviewMiddleService extends IService<EducationReviewMiddle> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-08-18
     */
    void add(EducationReviewMiddleParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-08-18
     */
    void delete(EducationReviewMiddleParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-08-18
     */
    void update(EducationReviewMiddleParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-18
     */
    EducationReviewMiddleResult findBySpec(EducationReviewMiddleParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-08-18
     */
    List<EducationReviewMiddleResult> findListBySpec(EducationReviewMiddleParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-18
     */
     LayuiPageInfo findPageBySpec(EducationReviewMiddleParam param);

}
