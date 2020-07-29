package cn.stylefeng.guns.modular.reviewdict.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.reviewdict.entity.ReviewDictionary;
import cn.stylefeng.guns.modular.reviewdict.model.params.ReviewDictionaryParam;
import cn.stylefeng.guns.modular.reviewdict.model.result.ReviewDictionaryResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评审字典表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-07-28
 */
public interface ReviewDictionaryService extends IService<ReviewDictionary> {

    /**
     * 新增
     *
     * @author wucy
     * @Date 2020-07-28
     */
    void add(ReviewDictionaryParam param);

    /**
     * 删除
     *
     * @author wucy
     * @Date 2020-07-28
     */
    void delete(ReviewDictionaryParam param);

    /**
     * 更新
     *
     * @author wucy
     * @Date 2020-07-28
     */
    void update(ReviewDictionaryParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author wucy
     * @Date 2020-07-28
     */
    ReviewDictionaryResult findBySpec(ReviewDictionaryParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author wucy
     * @Date 2020-07-28
     */
    List<ReviewDictionaryResult> findListBySpec(ReviewDictionaryParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author wucy
     * @Date 2020-07-28
     */
     LayuiPageInfo findPageBySpec(ReviewDictionaryParam param);

}
