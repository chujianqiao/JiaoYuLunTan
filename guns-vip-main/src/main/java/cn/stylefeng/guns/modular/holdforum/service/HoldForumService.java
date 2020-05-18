package cn.stylefeng.guns.modular.holdforum.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.holdforum.entity.HoldForum;
import cn.stylefeng.guns.modular.holdforum.model.params.HoldForumParam;
import cn.stylefeng.guns.modular.holdforum.model.result.HoldForumResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 承办方论坛表 服务类
 * </p>
 *
 * @author 
 * @since 2020-05-13
 */
public interface HoldForumService extends IService<HoldForum> {

    /**
     * 新增
     *
     * @author 
     * @Date 2020-05-13
     */
    void add(HoldForumParam param);

    /**
     * 删除
     *
     * @author 
     * @Date 2020-05-13
     */
    void delete(HoldForumParam param);

    /**
     * 更新
     *
     * @author 
     * @Date 2020-05-13
     */
    void update(HoldForumParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author 
     * @Date 2020-05-13
     */
    HoldForumResult findBySpec(HoldForumParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author 
     * @Date 2020-05-13
     */
    List<HoldForumResult> findListBySpec(HoldForumParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author 
     * @Date 2020-05-13
     */
     LayuiPageInfo findPageBySpec(HoldForumParam param);

}
