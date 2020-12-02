package cn.stylefeng.guns.modular.answer.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.answer.entity.Answer;
import cn.stylefeng.guns.modular.answer.model.params.AnswerParam;
import cn.stylefeng.guns.modular.answer.model.result.AnswerResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 自动回复表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-11-09
 */
public interface AnswerService extends IService<Answer> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-11-09
     */
    void add(AnswerParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-11-09
     */
    void delete(AnswerParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-11-09
     */
    void update(AnswerParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-11-09
     */
    AnswerResult findBySpec(AnswerParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-11-09
     */
    List<AnswerResult> findListBySpec(AnswerParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-11-09
     */
     LayuiPageInfo findPageBySpec(AnswerParam param);

    LayuiPageInfo findPageBySpecAll(AnswerParam param);

    Integer setStatus(Long answerId, String status);
}
