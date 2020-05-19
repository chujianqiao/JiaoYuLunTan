package cn.stylefeng.guns.modular.ownForum.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.mapper.OwnForumMapper;
import cn.stylefeng.guns.modular.ownForum.model.params.OwnForumParam;
import cn.stylefeng.guns.modular.ownForum.model.result.OwnForumResult;
import  cn.stylefeng.guns.modular.ownForum.service.OwnForumService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 自设论坛表 服务实现类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-18
 */
@Service
public class OwnForumServiceImpl extends ServiceImpl<OwnForumMapper, OwnForum> implements OwnForumService {

    @Override
    public void add(OwnForumParam param){
        OwnForum entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OwnForumParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OwnForumParam param){
        OwnForum oldEntity = getOldEntity(param);
        OwnForum newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OwnForumResult findBySpec(OwnForumParam param){
        return null;
    }

    @Override
    public List<OwnForumResult> findListBySpec(OwnForumParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OwnForumParam param){
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

    private Serializable getKey(OwnForumParam param){
        return param.getForumId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OwnForum getOldEntity(OwnForumParam param) {
        return this.getById(getKey(param));
    }

    private OwnForum getEntity(OwnForumParam param) {
        OwnForum entity = new OwnForum();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
