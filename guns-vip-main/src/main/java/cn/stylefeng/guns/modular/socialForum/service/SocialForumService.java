package cn.stylefeng.guns.modular.socialForum.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.socialForum.entity.SocialForum;
import cn.stylefeng.guns.modular.socialForum.model.params.SocialForumParam;
import cn.stylefeng.guns.modular.socialForum.model.result.SocialForumResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社会资助论坛表 服务类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-15
 */
public interface SocialForumService extends IService<SocialForum> {

    /**
     * 新增
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    void add(SocialForumParam param);

    /**
     * 删除
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    void delete(SocialForumParam param);

    /**
     * 更新
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    void update(SocialForumParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    SocialForumResult findBySpec(SocialForumParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    List<SocialForumResult> findListBySpec(SocialForumParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    Page<Map<String, Object>> findPageBySpec(SocialForumParam param);

}
