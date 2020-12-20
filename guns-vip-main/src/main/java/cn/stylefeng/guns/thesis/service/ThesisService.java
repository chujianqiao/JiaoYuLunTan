package cn.stylefeng.guns.thesis.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.model.result.ThesisResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-05-21
 */
public interface ThesisService extends IService<Thesis> {

    /**
     * 新增
     * @author wucy
     * @Date 2020-05-21
     */
    void add(ThesisParam param);

    /**
     * 删除
     * @author wucy
     * @Date 2020-05-21
     */
    void delete(ThesisParam param);

    /**
     * 更新
     * @author wucy
     * @Date 2020-05-21
     */
    void update(ThesisParam param);

    /**
     * 查询单条数据，Specification模式
     * @author wucy
     * @Date 2020-05-21
     */
    ThesisResult findBySpec(ThesisParam param);

    /**
     * 查询列表，Specification模式
     * @author wucy
     * @Date 2020-05-21
     */
    List<ThesisResult> findListBySpec(ThesisParam param);

    /**
     * 查询分页数据，Specification模式
     * @author wucy
     * @Date 2020-05-21
     */
     LayuiPageInfo findPageBySpec(ThesisParam param);

    /**
     * 拼接数据
     * @author wucy
     * @Date 2020-05-25
     */
    Page<Map<String, Object>> findPageWrap(ThesisParam param);

    Page<Map<String, Object>> findPageWrapByBatch(ThesisParam param, Integer batch);

    /**
     * 评审专家查询列表
     * @return
     */
    Page<Map<String, Object>> findReview(List<Long> list,ThesisParam thesisParam);

    List<String> findExistList();
}
