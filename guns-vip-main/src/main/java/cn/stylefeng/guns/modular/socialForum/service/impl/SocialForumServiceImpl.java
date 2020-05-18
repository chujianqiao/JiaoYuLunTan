package cn.stylefeng.guns.modular.socialForum.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.socialForum.entity.SocialForum;
import cn.stylefeng.guns.modular.socialForum.mapper.SocialForumMapper;
import cn.stylefeng.guns.modular.socialForum.model.params.SocialForumParam;
import cn.stylefeng.guns.modular.socialForum.model.result.SocialForumResult;
import  cn.stylefeng.guns.modular.socialForum.service.SocialForumService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 社会资助论坛表 服务实现类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-15
 */
@Service
public class SocialForumServiceImpl extends ServiceImpl<SocialForumMapper, SocialForum> implements SocialForumService {

    @Override
    public void add(SocialForumParam param){
        SocialForum entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SocialForumParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(SocialForumParam param){
        SocialForum oldEntity = getOldEntity(param);
        SocialForum newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SocialForumResult findBySpec(SocialForumParam param){
        return null;
    }

    @Override
    public List<SocialForumResult> findListBySpec(SocialForumParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(SocialForumParam param){
        Page pageContext = getPageContext();
        if (param.getForumName()==null){
            param.setForumName("%%");
        }else {
            param.setForumName("%" + param.getForumName() + "%");
        }

        IPage page = null;
        LoginUser user = LoginContextHolder.getContext().getUser();
        List roleIds = user.getRoleList();
        long adminRole = 1;
        if(roleIds.contains(adminRole)){
            page = this.baseMapper.customPageListAdmin(pageContext, param);
        }else {
            page = this.baseMapper.customPageList(pageContext, param);
        }

        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(SocialForumParam param){
        return param.getForumId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private SocialForum getOldEntity(SocialForumParam param) {
        return this.getById(getKey(param));
    }

    private SocialForum getEntity(SocialForumParam param) {
        SocialForum entity = new SocialForum();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
