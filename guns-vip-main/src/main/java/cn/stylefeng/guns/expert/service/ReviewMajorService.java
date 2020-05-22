package cn.stylefeng.guns.expert.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.model.result.ReviewMajorResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评审专家表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-05-11
 */
public interface ReviewMajorService extends IService<ReviewMajor> {

    /**
     * 新增
     *
     * @author wucy
     * @Date 2020-05-11
     */
    void add(ReviewMajorParam param);

    /**
     * 删除
     *
     * @author wucy
     * @Date 2020-05-11
     */
    void delete(ReviewMajorParam param);

    /**
     * 更新
     *
     * @author wucy
     * @Date 2020-05-11
     */
    void update(ReviewMajorParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author wucy
     * @Date 2020-05-11
     */
    ReviewMajorResult findBySpec(ReviewMajorParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author wucy
     * @Date 2020-05-11
     */
    List<ReviewMajorResult> findListBySpec(ReviewMajorParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author wucy
     * @Date 2020-05-11
     */
     LayuiPageInfo findPageBySpec(ReviewMajorParam param);


    /**
     * 查询分页数据
     *
     * @author wucy
     * @Date 2020-05-11
     */
    Page<Map<String, Object>> findPageWrap(ReviewMajorParam param ,String paramIds);

}
