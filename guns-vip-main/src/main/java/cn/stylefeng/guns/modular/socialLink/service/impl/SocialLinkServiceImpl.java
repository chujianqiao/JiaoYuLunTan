package cn.stylefeng.guns.modular.socialLink.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.socialLink.entity.SocialLink;
import cn.stylefeng.guns.modular.socialLink.mapper.SocialLinkMapper;
import cn.stylefeng.guns.modular.socialLink.model.params.SocialLinkParam;
import cn.stylefeng.guns.modular.socialLink.model.result.SocialLinkResult;
import  cn.stylefeng.guns.modular.socialLink.service.SocialLinkService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 合作方式表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-07-15
 */
@Service
public class SocialLinkServiceImpl extends ServiceImpl<SocialLinkMapper, SocialLink> implements SocialLinkService {

    @Override
    public void add(SocialLinkParam param){
        SocialLink entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SocialLinkParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(SocialLinkParam param){
        SocialLink oldEntity = getOldEntity(param);
        SocialLink newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SocialLinkResult findBySpec(SocialLinkParam param){
        return null;
    }

    @Override
    public List<SocialLinkResult> findListBySpec(SocialLinkParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(SocialLinkParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public LayuiPageInfo findPageBySpecAll(SocialLinkParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageListAll(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(SocialLinkParam param){
        return param.getLinkId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private SocialLink getOldEntity(SocialLinkParam param) {
        return this.getById(getKey(param));
    }

    private SocialLink getEntity(SocialLinkParam param) {
        SocialLink entity = new SocialLink();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public Integer setStatus(Long linkId, String status) {
        return this.baseMapper.setStatus(linkId, status);
    }

}
