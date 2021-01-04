package cn.stylefeng.guns.modular.ownForum.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.model.params.OwnForumParam;
import cn.stylefeng.guns.modular.ownForum.model.result.OwnForumResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自设论坛表 服务类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
public interface OwnForumService extends IService<OwnForum> {

    /**
     * 新增
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    void add(OwnForumParam param);

    /**
     * 删除
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    void delete(OwnForumParam param);

    /**
     * 更新
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    void update(OwnForumParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    OwnForumResult findBySpec(OwnForumParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    List<OwnForumResult> findListBySpec(OwnForumParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
     LayuiPageInfo findPageBySpec(OwnForumParam param);

    Page<Map<String, Object>> findPageWrap(OwnForumParam param);
}
