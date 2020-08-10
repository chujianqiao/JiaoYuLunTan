package cn.stylefeng.guns.modular.forum.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.forum.entity.Forum;
import cn.stylefeng.guns.modular.forum.model.params.ForumParam;
import cn.stylefeng.guns.modular.forum.model.result.ForumResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分论坛表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-08-10
 */
public interface ForumService extends IService<Forum> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-08-10
     */
    void add(ForumParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-08-10
     */
    void delete(ForumParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-08-10
     */
    void update(ForumParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-10
     */
    ForumResult findBySpec(ForumParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-08-10
     */
    List<ForumResult> findListBySpec(ForumParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-08-10
     */
     LayuiPageInfo findPageBySpec(ForumParam param);

    /**
     * 拼接数据
     * @author wucy
     * @Date 2020-05-25
     */
    Page<Map<String, Object>> findPageWrap(ForumParam param);

    Integer setStatus(Long forumId, Integer status);
}
