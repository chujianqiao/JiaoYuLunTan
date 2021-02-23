package cn.stylefeng.guns.modular.socialLink.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.socialLink.entity.SocialLink;
import cn.stylefeng.guns.modular.socialLink.model.params.SocialLinkParam;
import cn.stylefeng.guns.modular.socialLink.model.result.SocialLinkResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 合作方式表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-07-15
 */
public interface SocialLinkService extends IService<SocialLink> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-07-15
     */
    void add(SocialLinkParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-07-15
     */
    void delete(SocialLinkParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-07-15
     */
    void update(SocialLinkParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-15
     */
    SocialLinkResult findBySpec(SocialLinkParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-07-15
     */
    List<SocialLinkResult> findListBySpec(SocialLinkParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-15
     */
     LayuiPageInfo findPageBySpec(SocialLinkParam param);
    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-15
     */
    LayuiPageInfo findPageBySpecAll(SocialLinkParam param);

    /**
     * 修改环节状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    Integer setStatus(Long linkId, String status);

}
